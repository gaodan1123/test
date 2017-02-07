
package com.ppm.integration.agilesdk.connector.agilecentral;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ppm.integration.agilesdk.connector.agilecentral.model.Defect;
import com.ppm.integration.agilesdk.connector.agilecentral.model.HierarchicalRequirement;
import com.ppm.integration.agilesdk.connector.agilecentral.model.Iteration;
import com.ppm.integration.agilesdk.connector.agilecentral.model.Project;
import com.ppm.integration.agilesdk.connector.agilecentral.model.Release;
import com.ppm.integration.agilesdk.connector.agilecentral.model.Subscription;
import com.ppm.integration.agilesdk.connector.agilecentral.model.Task;
import com.ppm.integration.agilesdk.connector.agilecentral.model.TimeEntryItem;
import com.ppm.integration.agilesdk.connector.agilecentral.model.TimeEntryValue;
import com.ppm.integration.agilesdk.connector.agilecentral.model.User;
import com.ppm.integration.agilesdk.connector.agilecentral.model.Workspace;
import com.ppm.integration.agilesdk.provider.Providers;
import com.ppm.integration.agilesdk.provider.UserProvider;

public class RallyClient {

    private final RestHelper helper;

    public RallyClient(String endpoint, Config config) {
        this.helper = new RestHelper(endpoint, config);
    }

    /**
     * get the subscription
     * @return the Subscription encoded string
     */
    public Subscription getSubscription() {
        String subscriptionURI = "/slm/webservice/v2.0/subscription";
        return new Subscription(helper.get(subscriptionURI).getJSONObject("Subscription"));
    }

    public List<Workspace> getWorkspaces(Subscription subscription) {
        return getWorkspaces(subscription.getId());
    }

    /**
     * get the all the workspaces for a specified subscription
     * @param subscriptionId
     * @return
     */
    public List<Workspace> getWorkspaces(String subscriptionId) {
        String workspacesURI = "/slm/webservice/v2.0/Subscription/?/Workspaces";
        workspacesURI = workspacesURI.replace("?", subscriptionId);
        JSONArray jsonArray = helper.getAll(workspacesURI);
        List<Workspace> workspaces = new ArrayList<Workspace>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            workspaces.add(new Workspace(jsonArray.getJSONObject(i)));
        }
        return workspaces;
    }

    public List<Project> getProjects(Workspace workspace) {
        return getProjects(workspace.getId());
    }

    /**
     * get the all the projects for a specified workspace
     * @param workspaceId
     * @return
     */
    public List<Project> getProjects(String workspaceId) {
        String projectsURI = "/slm/webservice/v2.0/Workspace/?/Projects";
        projectsURI = projectsURI.replace("?", workspaceId);
        JSONArray jsonArray = helper.getAll(projectsURI);
        List<Project> projects = new ArrayList<Project>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            projects.add(new Project(jsonArray.getJSONObject(i)));
        }
        return projects;
    }

    /**
     * get the all the iterations for a specified project
     * @param projectId
     * @return
     */
    public List<Iteration> getIterations(String projectId) {
        String iterationsURI = "/slm/webservice/v2.0/project/?/Iterations";
        iterationsURI = iterationsURI.replace("?", projectId);
        JSONArray jsonArray = helper.getAll(iterationsURI);
        List<Iteration> iterations = new ArrayList<Iteration>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            iterations.add(new Iteration(jsonArray.getJSONObject(i)));
        }
        List<HierarchicalRequirement> hierarchicalRequirements = getHierarchicalRequirements();
        fillHierarchicalRequirement(iterations, hierarchicalRequirements);
        List<User> users = getUsers();
        fillUser(hierarchicalRequirements, users);
        return iterations;
    }

    /**
     * get the all the HierarchicalRequirements (User story)
     * @return
     */
    public List<HierarchicalRequirement> getHierarchicalRequirements() {
        String hierarchicalrequirementURI = "/slm/webservice/v2.0/hierarchicalrequirement";
        JSONArray jsonArray = helper.query(hierarchicalrequirementURI, "", true, "", 1, 20);
        List<HierarchicalRequirement> hierarchicalRequirements = new ArrayList<HierarchicalRequirement>();

        UserProvider userProvider = Providers.getUserProvider(RallyIntegrationConnector.class);
        for (int i = 0; i < jsonArray.size(); i++) {

            hierarchicalRequirements.add(new HierarchicalRequirement(jsonArray.getJSONObject(i), userProvider));
        }
        return hierarchicalRequirements;
    }

    /**
     * get the all the users
     * @return
     */
    private List<User> getUsers() {
        String userURI = "/slm/webservice/v2.0/user";
        JSONArray jsonArray = helper.query(userURI, "", true, "", 1, 20);
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < jsonArray.size(); i++) {
            users.add(new User(jsonArray.getJSONObject(i)));
        }
        return users;
    }

    /**
     * put hierarchicalRequirements to the corresponding iteration
     * @param iterations
     * @param hierarchicalRequirements
     */
    private void fillHierarchicalRequirement(List<Iteration> iterations,
            List<HierarchicalRequirement> hierarchicalRequirements)
    {
        for (HierarchicalRequirement hierarchicalRequirement : hierarchicalRequirements) {
            for (Iteration iteration : iterations) {
                iteration.addHierarchicalRequirement(hierarchicalRequirement);
            }
        }
    }

    /**
     * put users to the corresponding hierarchicalRequirement
     * @param hierarchicalRequirements
     * @param users
     */
    private void fillUser(List<HierarchicalRequirement> hierarchicalRequirements, List<User> users) {
        for (User user : users) {
            for (HierarchicalRequirement hierarchicalRequirement : hierarchicalRequirements) {
                user.addHierarchicalRequirement(hierarchicalRequirement);
            }
        }
    }

    /**
     * get all the releases for a specified project
     * @param projectId
     * @return
     */
    public List<Release> getReleases(String projectId) {
        String releasesURI = "/slm/webservice/v2.0/project/?/Releases";
        releasesURI = releasesURI.replace("?", projectId);
        JSONArray jsonArray = helper.getAll(releasesURI);
        List<Release> releases = new ArrayList<Release>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            releases.add(new Release(jsonArray.getJSONObject(i)));
        }
        return releases;
    }

    /**
     * get iterations for specified release according to time
     * @param projectId
     * @param releaseId
     * @return
     */
    public List<Iteration> getIterationsByRelease(String projectId, String releaseId) {
        String iterationsURI = "/slm/webservice/v2.0/project/?/Iterations";
        iterationsURI = iterationsURI.replace("?", projectId);
        JSONArray jsonArray = helper.getAll(iterationsURI);
        List<Iteration> iterations = new ArrayList<Iteration>();
        List<HierarchicalRequirement> hierarchicalRequirements = getHierarchicalRequirements();
        List<User> users = getUsers();

        String releaseURI = "/slm/webservice/v2.0/release/" + releaseId;
        JSONObject releaseObject = helper.get(releaseURI).getJSONObject("Release");
        if (releaseObject.isNullObject()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                iterations.add(new Iteration(jsonArray.getJSONObject(i)));
            }
        } else {
            Release release = new Release(releaseObject);
            Date releaseStart = release.getScheduleStart();
            Date releaseEnd = release.getScheduleFinish();

            for (int i = 0; i < jsonArray.size(); i++) {
                Iteration iteration = new Iteration(jsonArray.getJSONObject(i));
                Date iterationStart = iteration.getScheduleStart();
                if (iterationStart.getTime() > releaseStart.getTime()
                        && iterationStart.getTime() < releaseEnd.getTime()) {
                    iterations.add(iteration);
                }
            }
        }

        fillHierarchicalRequirement(iterations, hierarchicalRequirements);
        fillUser(hierarchicalRequirements, users);
        return iterations;
    }

    /**
     * get all tasks for a specified hierarchicalRequirement
     * @param hierarchicalRequirementId
     * @return
     */
    public List<Task> getTasks(String hierarchicalRequirementId) {
        String tasksURI = "/slm/webservice/v2.0/hierarchicalRequirement/?/tasks";
        tasksURI = tasksURI.replace("?", hierarchicalRequirementId);
        JSONArray jsonArray = helper.getAll(tasksURI);
        List<Task> tasks = new ArrayList<Task>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            tasks.add(new Task(jsonArray.getJSONObject(i)));
        }
        return tasks;
    }

    /**
     * all TimeEntryItems corresponding to a specified work product (user story
     * or defect)
     * @return String : the UUID of work product
     */
    public HashMap<String, List<TimeEntryItem>> getTimeEntryItem() {
        HashMap<String, List<TimeEntryItem>> hms = new HashMap<>();
        String timeEntryItemURI = "/slm/webservice/v2.0/timeentryitem";
        JSONArray jsonArray = helper.query(timeEntryItemURI, "", true, "", 1, 20);

        for (int i = 0; i < jsonArray.size(); i++) {
            TimeEntryItem item = new TimeEntryItem(jsonArray.getJSONObject(i));
            String WorkProductUUID = item.getWorkProductUUID();
            if (hms.containsKey(item.getWorkProductUUID())) {
                List<TimeEntryItem> items = hms.get(item.getWorkProductUUID());
                items.add(item);
                hms.put(WorkProductUUID, items);
            } else {
                List<TimeEntryItem> items = new ArrayList<>();
                items.add(item);
                hms.put(WorkProductUUID, items);
            }
        }
        return hms;
    }

    /**
     * get all timeEntryValues correspond to a specified timeEntryItem
     * @param timeEntryItemId
     * @return String : the UUID of timeEntryItem
     */
    public HashMap<String, List<TimeEntryValue>> getTimeEntryValue() {
        HashMap<String, List<TimeEntryValue>> hms = new HashMap<>();
        String timeEntryValueURI = "/slm/webservice/v2.0/timeentryvalue";
        JSONArray jsonArray = helper.query(timeEntryValueURI, "", true, "", 1, 20);

        for (int i = 0; i < jsonArray.size(); i++) {
            TimeEntryValue value = new TimeEntryValue(jsonArray.getJSONObject(i));
            String TimeEntryItemUUID = value.getTimeEntryItemUUID();
            if (hms.containsKey(TimeEntryItemUUID)) {
                List<TimeEntryValue> timeEntryValues = hms.get(TimeEntryItemUUID);
                timeEntryValues.add(value);
                hms.put(TimeEntryItemUUID, timeEntryValues);
            } else {
                List<TimeEntryValue> timeEntryValues = new ArrayList<>();
                timeEntryValues.add(value);
                hms.put(TimeEntryItemUUID, timeEntryValues);
            }
        }
        return hms;
    }

    /**
     * get all hierarchicalRequirements correspond to a specified iteration
     * @return String : the UUID of iteration
     */
    public HashMap<String, List<HierarchicalRequirement>> getHierarchicalRequirement() {
        HashMap<String, List<HierarchicalRequirement>> hms = new HashMap<>();
        String HierarchicalRequirementURI = "/slm/webservice/v2.0/hierarchicalRequirement";
        JSONArray jsonArray = helper.query(HierarchicalRequirementURI, "", true, "", 1, 20);
        UserProvider userProvider = Providers.getUserProvider(RallyIntegrationConnector.class);

        for (int i = 0; i < jsonArray.size(); i++) {
            HierarchicalRequirement hierarchicalRequirement =
                    new HierarchicalRequirement(jsonArray.getJSONObject(i), userProvider);
            String IterationUUID = hierarchicalRequirement.getIterationUUID();
            if (hms.containsKey(hierarchicalRequirement.getIterationUUID())) {
                List<HierarchicalRequirement> hierarchicalRequirements =
                        hms.get(hierarchicalRequirement.getIterationUUID());
                hierarchicalRequirements.add(hierarchicalRequirement);
                hms.put(IterationUUID, hierarchicalRequirements);
            } else {
                List<HierarchicalRequirement> hierarchicalRequirements = new ArrayList<>();
                hierarchicalRequirements.add(hierarchicalRequirement);
                hms.put(IterationUUID, hierarchicalRequirements);
            }
        }
        return hms;
    }

    /**
     * get all defects
     * @return
     */
    public List<Defect> getDefects() {
        String defectURI = "/slm/webservice/v2.0/defect";
        JSONArray jsonArray = helper.query(defectURI, "", true, "", 1, 20);
        List<Defect> defects = new ArrayList<Defect>();

        UserProvider userProvider = Providers.getUserProvider(RallyIntegrationConnector.class);
        for (int i = 0; i < jsonArray.size(); i++) {

            defects.add(new Defect(jsonArray.getJSONObject(i), userProvider));
        }
        return defects;
    }

    /**
     * get all defects correspond to a specified iteration
     * @return String : the UUID of iteration
     */
    public HashMap<String, List<Defect>> getDefect() {
        HashMap<String, List<Defect>> hms = new HashMap<>();
        String defectURI = "/slm/webservice/v2.0/defect";
        JSONArray jsonArray = helper.query(defectURI, "", true, "", 1, 20);
        UserProvider userProvider = Providers.getUserProvider(RallyIntegrationConnector.class);

        for (int i = 0; i < jsonArray.size(); i++) {
            Defect defect = new Defect(jsonArray.getJSONObject(i), userProvider);
            String IterationUUID = defect.getIterationUUID();
            if (hms.containsKey(defect.getIterationUUID())) {
                List<Defect> defects = hms.get(defect.getIterationUUID());
                defects.add(defect);
                hms.put(IterationUUID, defects);
            } else {
                List<Defect> defects = new ArrayList<>();
                defects.add(defect);
                hms.put(IterationUUID, defects);
            }
        }
        return hms;
    }
}
