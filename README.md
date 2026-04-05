# Final Year Project
## Building a Mobile Application - Star Student

 **Leane Matejko - Supervisor: Susnas Sourjah**

### Pre-requisites
- **Android Phone**
- App_debug.apk (Project Executable) file ->    [Project path - PROJECT/product/app/build/outputs/apk/debug](PROJECT/product/app/build/outputs/apk/debug)
    - No additional downloads are required.
- On you android phone, allow security permissions to be lowered to **allow non-Google Play Apps**
- **Android Version: 16.0** 


### Installation instructions

1. Download App_debug.apk file.
2. Allow non-Google Play apps to run on your phone.
3. Open file from folder.
4. Trust Star Student
5. Ready to go!

### Usage Instructions
- [Sign In and Register](#sign-in-and-register)
- [Profile Settings](#user-profile)
- [Study Centre](#study-centre)
- [Planner and Task List](#planner-and-task-list)

#### Sign In and Register
- Register Account (New User)


    <img src="readmeImages/image-4.png" alt="Register Image" width="25%"/>
    <img src="readmeImages/image-5.png" alt="Setting Password" width="25%"/>

- Sign in using credentials (Returning User)
    
    
    <img src="readmeImages/image-3.png" alt="Sign In Page" width="25%"/>

- Navigate the Homepage


    <img src="readmeImages/homepage.png" alt="Homepage" width="25%"/>

- Light and Dark mode (Based on your device setting) 


    <img src="readmeImages/image.png" alt="LightMode" width="25%"/>
    <img src="readmeImages/image-1.png" alt="DarkMode" width="25%"/>


#### Profile Settings
- Homepage Reformated

    <div style="display:flex; align-items:center; gap:5px;"><img src="readmeImages/image-2.png" alt="Original Homepage" width="25%"/> > <img src="readmeImages/homepage.png" alt="Current Homepage" width="25%"/>
    <ul>
        <li>Widgets have been restructed for clearer results</li>
        <li>Scrolling possible</li>
        <li>Top Banner added for additional navigation</li>
    </ul>
    </div>


- Profile Page
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/ProfileSettings.png" alt="ProfileSettings" width="25%"/>
    <ul>
        <li>Username (Default to user's email)</li>
        <li>Birthday (Default to when the user first signed up)</li>
        <li>Location Access (Default is denied)</li>
        <li>Email (Cannot be changed.)</li>
    </ul>
    </div>

- Top Banner (Navigation to profile page)
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/TopBanner.png" alt="TopBanner" width="25%"/>
    <ul>
        <li>Shows user's chosen username</li>
        <li>Date and Time update to reflect realtime</li>
        <li>Profile picture navigates to profile page</li>
    </ul>
    </div>

- Update profile settings
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/UpdateProfile.png" alt="UpdateProfile" width="25%"/>
    <ul>
        <li>Can update username, birthday and location access</li>
        <li>Birthday limited to number input (12/10/2003 -> 12th October 2003)</li>
    </ul>
    </div>

- Avatar setting (Placeholder for future options)
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/AvatarSettings.png" alt="AvatarSettings" width="25%"/>
    <ul>
        <li>Placeholder for a future customisable avatar</li>
    </ul>
    </div>

### Study Centre

- Location Detector (Not Detected)
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/LocationDetectorNotDetected.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>This detector takes the user's GPS signal and checked if they are in one of the saved Study Spaces.</li>
        <li>The detector will show 'Not detected' if the user has the location access turned off or is not within a study space.</li>
    </ul>
    </div>
- Location Detector (Detected)
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/LocationDetectorDetected.png" alt="LocationDetectorDetected" width="25%"/>
    <ul>
        <li>The detectors label will update to detect if within a study space.</li>
        <li>A study sessios can be automatically started if the user is within a study space for at least 5 minutes.</li>
    </ul>
    </div>
- Saved Locations
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/SavedLocations.png" alt="SavedLocations" width="25%"/>
    <ul>
        <li>User can save up to 5 locations as their study spaces.</li>
        <li>Each study space has a label that can be updated via the input field.</li>
        <li>Each study space's location can be updated to the user's current location.</li>
        <li>Window cannot be opened if location access is off.</li>

    </ul>
    </div>
- Study Session Window (No Sessions)
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/StudySessionWindow.png" alt="StudySessionWindow" width="25%"/>
    <ul>
        <li>Users can start a study session manuall.</li>
        <li>A study session will be automatically started if the user is within a study space for 5 minutes.</li>
    </ul>
    </div>
- Study Session Window (Current Sessions Active)
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/StudySessionWindowActiveSession.png" alt="AvatarSettings" width="25%"/>
    <ul>
        <li>Timer reflects total study time, formatted HH:mm:ss.</li>
        <li>Options to pause.</li>
    </ul>
    </div>
- Study Session Window (Current Sessions Paused)
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/StudySessionWindowPaused.png" alt="StudySessionWindowPaused" width="25%"/>
    <ul>
        <li>Button and sessions timer update to reflect the sessions paused status. </li>
    </ul>
    </div>

- Study Session Tasks (Placeholder)
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/StudySessionTasks.png" alt="StudySessionTasks" width="25%"/>
    <ul>
        <li>Placeholder for user tasks.</li>
    </ul>
    </div>

- Recent Study Sessions
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/RecentStudySessions.png" alt="RecentStudySessions" width="25%"/>
    <ul>
        <li>Shows the 5 most recent study sessions completed by the user.</li>
        <li>Study Sessions must be over 5 minutes to be shown.</li>
    </ul>
    </div>

### Planner and Task List

- Planner - Calendar View
    
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/Planner_Page.png" alt="LocationDetectorNotDetected" width="25%"/>
    <img src="readmeImages/CalendarMonthNavigation.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Calender view of all tasks</li>
        <li>Loads to the current month</li>
        <li>Indicator for the current day (diamond) and days with tasks due (circle)</li>
        <li>Click the month and year at the top to open the selection window </li>
        <li>Options to add tasks to day with or without tasks</li>
    </ul>
    </div>

- Calendar Navigation
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/CalendarMonthNavigation.png" alt="LocationDetectorNotDetected" width="25%"/>
    <img src="readmeImages/CalendarMonthYearSelection.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Navigation possible via the next and previous buttons or clicking on the month and selecting an option</li>
        <li>Possible to select the month or year</li>
        <li>Months can be selected via buttons and the year via a dropdown</li>
        <li>Dismiss the window by clicking outside</li>
    </ul>
    </div>

- Planner - Navigation to Task List
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/Navigation_TaskList.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Navigation to the Task List view, separated into categories.</li>
    </ul>
    </div>

- Planner - Task Catergories
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/PlannerMainCategories.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Shows current tasks in a managble catergories: overdue, weekly, today and this week</li>
        <li>There is an indicator on the widget for the total number of tasks and of which that are critical</li>
    </ul>
    </div>

- Planner - Task Catergories (Examples)
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/OverdueTasks.png" alt="LocationDetectorNotDetected" width="25%"/>
    <img src="readmeImages/MonthTaskList.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Examples of the task categories list. Sorted by due dates.</li>
        <li>Overdue tasks are highlighted, and ciritcal tasks have a star indicator.</li>
    </ul>
    </div>

- Task List View
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/TaskListView.png" alt="LocationDetectorNotDetected" width="25%"/>
    <img src="readmeImages/TaskOptionHighlighted.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Shows tasks into their categories, sorted by due date.</li>
        <li>Overdue tasks are highlighted, and ciritcal tasks have a star indicator.</li>
        <li>Categories and tasks can be edited by clicking on the badge</li>
        <li>Tasks can be marked off using the toggle on the left side</li>
        <li>Quick add tasks with the add button on the side of the category or with the add button on the bottom right hand corner</li>
    </ul>
    </div>

- Task List View
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/Add_Task_Options.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Window to add a new category or task</li>
    </ul>
    </div>

- Task List - Add New Category
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/AddNewCategory.png" alt="LocationDetectorNotDetected" width="25%"/>
    <img src="readmeImages/EditCategory.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Window to add a new category</li>
        <li>Shows a preview of with the category badge renders to</li>
        <li>Colour options of red, orange, yellow, green, blue, navy, purple, pink (Adaptive to the light and dark themes)</li>
        <li>Badges can be left to their default and edtted later.</li>
        <li>When in edit mode, categories can be hidden</li>
    </ul>
    </div>

- Task List - Task Item
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/TasksItemTaskList.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Tasks are shown under each category</li>
        <li>Each task shows the label, due date, critical status and toggle for the complete status.</li>
        <li>Tasks are edittable by clicking on the widget</li>
    </ul>
    </div>

- Task List - Add New Task
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/AddNewTaskCateLabel.png" alt="LocationDetectorNotDetected" width="25%"/>
    <img src="readmeImages/AddNewTaskDateTime.png" alt="LocationDetectorNotDetected" width="25%"/>
    <img src="readmeImages/AddNewTaskCritical.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Options to select the category, label, date, time and critical status</li>
        <li>A category must be selected, otherwise and error is thrown</li>
        <li>Date and time is set to the current date and time as a default</li>
    </ul>
    </div>

- Task List - Edit Task
    <div style="display:flex; align-items:center; gap:10px;">
    <img src="readmeImages/EditTasksCategoryLabel.png" alt="LocationDetectorNotDetected" width="25%"/>
    <img src="readmeImages/EditTaskCriticalComplete.png" alt="LocationDetectorNotDetected" width="25%"/>
    <ul>
        <li>Options to change the category, label, date, time and critical status and complete a task</li>
        <li>A All currently saved values are loaded into each field</li>
        <li>If the task is complete, the completion date set to the current time</li>
    </ul>
    </div>

