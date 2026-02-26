///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.tamboui:tamboui-toolkit:0.2.0-SNAPSHOT
//DEPS dev.tamboui:tamboui-jline3-backend:0.2.0-SNAPSHOT
//DEPS org.kohsuke:github-api:2.0-rc.5



import dev.tamboui.style.Color;
import dev.tamboui.style.Overflow;
import dev.tamboui.toolkit.app.ToolkitApp;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.elements.Panel;
import dev.tamboui.toolkit.elements.TextInputElement;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import dev.tamboui.widgets.input.TextInputState;

import org.kohsuke.github.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static dev.tamboui.toolkit.Toolkit.*;


//BeJUG/bejug.github.io
public class GithubInspector extends ToolkitApp {
    
    private String placeholder = "Search 'owner/repo'...";
    private String status = "Waiting";
    private List<String> tasks = new ArrayList<>();
    private List<GHIssue> issues = new ArrayList<>();
    private int currentElement = 0;
    private int maxElement = 0;    
    private View currentView = View.HOME;
    private String repoName =""; 
    private TextInputState inputState = new TextInputState();  

    private TextInputElement searchField = textInput(inputState)
                                            .onSubmit(() -> fetchGithubIssues(inputState.text()))
                                            .placeholder(placeholder);

    @Override
    public Element render() {

        List<Element> page = new ArrayList<>();
 
        var header = panel("Repository", searchField);


         Panel currentPanel = switch (currentView) {
            case DETAILS ->  this.getIssueDetails();
            case HOME ->  this.getIssuesList();
        };
        if(currentView == View.HOME){
            page.add(header);
        }
        page.add(currentPanel.onKeyEvent(this::onSelectionList));
        page.add(getFooter());

        return panel("Github Issues Inspector", page.toArray(new Element[page.size()]))
                .rounded()
                .borderColor(Color.CYAN);

    }


    private Element getFooter(){
       return columns(
            text("[UP]/[DOWN] to select"),
            text("[d] to see details"),
            text("[h] to go main screen"),
            text("Status: "+status)
        );     
    }
    
    private Panel getIssuesList(){
        var items = list(tasks)
                    .highlightColor(Color.CYAN)
                    .focusable()
                    .id("TaskList")
                    .autoScroll()
                    .selected(currentElement)
                    .onKeyEvent(this::onSelectionList);
        return panel("Result", items)
                        .focusable()
                        .rounded()
                        .focusedBorderColor(Color.YELLOW)
                        .fill();            
    }

    private Panel getIssueDetails(){
        if(this.issues.size()<=0) return panel("No result");
        var issue = this.issues.get(currentElement);
        var panels = new ArrayList<Panel>();
        panels.add(panel("Assignees", text(issue.getAssignees().stream().map(this::getGHUserName).collect(Collectors.joining(",")))));
        panels.addAll(getComments(issue));

        return panel(
            issue.getTitle(),
            columns( 
                panels.toArray(new Element[panels.size()])
            ).focusable()
        );
  
    }

    private List<Panel> getComments(GHIssue issue){
        try{
            return issue.getComments().stream().map(this::getCommentPanel).toList();
        }catch (IOException e){
            return Collections.EMPTY_LIST;
        }
    }

    private Panel getCommentPanel(GHIssueComment comment){
        var text = text(comment.getBody()).dim().overflow(Overflow.WRAP_WORD);
        return panel(this.getCommentTitle(comment),text );
    }

    private String getCommentTitle(GHIssueComment comment){
        try {
            var user = comment.getUser().getName();
            if( user == null)
                user = comment.getUser().getLogin();
            return user+ " - " + comment.getCreatedAt();    
        } catch (IOException e) {
            return "unknown";
        }
    }
    
    private String getGHUserName(GHUser user){
        try{
            return user.getName();
        }catch(IOException e){
            return "unknown";
        }
    }

    void main() throws Exception  {
        new GithubInspector().run();
    }


    private EventResult onSelectionList(KeyEvent e){
        searchField.focusable(false);
        if(e.isDown()){
            currentElement = currentElement >= maxElement-1 ? maxElement-1: currentElement+1; 
            currentView = View.HOME;
            return EventResult.FOCUS_NEXT;
        }
        if(e.isUp()){
            currentElement = currentElement <= 0 ? 0: currentElement-1; 
            currentView = View.HOME;
            return EventResult.FOCUS_PREVIOUS;
        }
        if(e.isCharIgnoreCase('d')){
            status = "Details";
            currentView = View.DETAILS;
            return EventResult.HANDLED;
        }
        if(e.isCharIgnoreCase('h')){
            status = "Home";
            currentView = View.HOME;
            return EventResult.HANDLED;
        }
        currentView = View.HOME;
        status="Oups";
        return EventResult.UNHANDLED;
    }

    private void fetchGithubIssues(String name) {
        this.status = "Fetching data";
        try {
            repoName = name;
            var repo = GitHub.connect("lomagnette", System.getenv("GITHUB_OAUTH_KEY")).getRepository(repoName);
            
            issues.clear();
            issues.addAll(repo.getIssues(GHIssueState.OPEN));
            tasks.clear();
            tasks.addAll(issues.stream().map(e -> e.getTitle()).toList());
            this.status = issues.size() + " issues found";
            this.currentElement=0;
            this.maxElement=issues.size();
            this.searchField.focusable(false);

        } catch (Exception e) {
            this.status = "An error occured: "+ e;
        }
    }
}

enum View {HOME, DETAILS}