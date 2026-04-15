# Diary 

### Week 1 - 22/09
- Inital research about the projects available.
- Understanding the specific project requirements and the format needed for the final year project.
- **Next steps:** Create a clear plan about a final decision for the project.

### Week 2 - 29/09
- Completing further research and brain-storming ideas for possible mobile apps, and understanding what can be useful.
- **Next steps:** Begin creating the project plan and requirements to meet.

### Week 3 - 06/10
- Created the initial project plan outlining the abstract, timeline, possible risks and mitigations, resources for future research.
- Had first meeting with my supervisor, understood some of the missing features -> reworked the project plan requirements.
- **Next steps:** Set up project repository for future development -> Set up for SE practises to be included.

### Week 4 - 13/10
- Formatted the project repositiory for FYP, created initial android studio project, updating the gitignore and beginning to create the pipeline, looking for possible testing methods and devices. Revised some software engineering best practises. Troubleshooting pipeline setup -> not able to resolve. 
- **Next steps:** Fix pipeline issue, complete further research.

### Week 5 - 20/10
- Completing further reasearch about pipelines in Android apps. At this current state, Google play integration will not work, will be readdressed at a later date. Researching different solutions to address the security problems relating to the storage of user data. Created process flow diagram.
- **Next steps:** Complete implentation of basic UI features. *(Placeholders if necessary)*

### Week 6 - 27/10
- Researching into the database options avaliable, restructuring plan to reflect the remaining time avaliable.
- **Next steps:** Complete prototype and update requirements

### Week 7 - 03/11
- Prototype created with Figma using process flow diagram previously created. Outlines the serveral features outlined in the project, and creates a possible layout for the UI.
- **Next steps:** Create the user accounts and connect the database.

### Week 8 - 10/11
- Found cloud database options, and decided to use Firebase due to the volume of documentation and pre-existing intergrations.
- **Next steps:** Developing the UI and navigation.

### Week 9 - 17/11
- Completed research about architectual models and best practises. Currently untilising clean architecture methodology with the project using a MVVM structure for communciation and separation. Basic UI for the sign in and register pages completed, with some base of the model and viewmodels completed. Reusable UI components are also available outside of the currently completed ones. A navigtion system has been implemented, and users are able to move around sign in/ register portion of the app.
- **Next steps:** Create the user accounts and complete the model for sign in and register .

### Week 10 - 24/11
- Created initial implementation for the email verification model. Completed research about email sending implementation -> function needs to be readdressed.
- Singleton class added to prevent potential dataleaks of the main activity.
- **Next steps:** Implement unit test cases for current implementation. Complete interim report, retrospective and presentation.

### Week 11 - 01/12
- Implemented unit testing for model and UI elements. Jacoco dependency added to check for UI coverage. Readdresses pipeline issue -> still has not been resolved.
- Created Interim report, presentation and retrospective for the end of term review.
- **Next steps:** Clean up project for the first term review, along with the user manual. Complete the ViewModels for current implementation.

### Week 12 - 08/12
- Interim report and presentation.
- **Next steps:** Complete a submission for the interim report.

### Week 13 - 15/12
- Completed the interim report and connected the viewModel to the register and sign in views to their model with minimal unit testing.
- **Next steps:** Catch up with development work.

### Week 19 - 26/01
- Acting on feedback from the interim submission. Restructuring final report and planning feature features and requirements. Continuing unit testing for maximum coverage.
- **Next steps:** Continue fixing issues and working on feedback, researching functionality for next features. 

### Week 20 - 02/02
- Completing unit testing for the current features along with some basic integration testing for the navigation and loading. Updated sign features to prevent duplicate users. Completing research about GPS functionality.
- **Next steps:** Implementing GPS functionality and local db connection.

### Week 21 - 09/02
- Completed unit testing for the sign in and resigster model and UI.
- **Next steps:** Improve and refactor sign in and register 
### Week 22 - 16/02
- Research for better UI formatting and icon options.
- **Next steps:** Fix homepage formatting.
### Week 23 - 23/02
- Illness, leading to minimal development. Some research completed for location tracking and development.
- **Next steps:** Include improved navigation and top banner.
### Week 24 - 02/03
- Top banner and scrolling region avaliable when signed into the main app. Created initial screen for basic user information.
- **Next steps:** Local database connection.
### Week 25 - 09/03
- Room database implemented, local database can not be used to store app information -> Firebase only required for initial sign in.
- User information represents stored db values and has an updatable dialog window for the user's username, birthday and location access.
- **Next steps:** Navigation menu dropdown, study centre screen.
### Week 26 - 16/03
- Navigation menu implemented with main app pages.
- Inintal study centre created with basic session tracking and location tracking.
- **Next steps:** Refactor study centre. 
### Week 27 - 23/03
- Refactored study centre to move model logic out of the viewModel. 
- Planning future DB tables for planners.
- **Next steps:** Add calendar view
### Week 28 - 30/03
- Functional calendar and task list pages added. Possible to add categories and task, with editable fields.
- Unwanted Categories can be hidden.
- **Next steps:** Add history page.
### Week 29 - 06/04
- History page and study session history page added. Adds a monthly view and is possible to delete completed sessions.
- Custom theme options that load once the user is signed in, along with an editable layout for the homepage.
- **Next steps:** Finalising project code.
### Week 30 - 13/04
- Final project refactoring and bug fixing, with additional theme options. 
- Completing final report and demo view.
- **Next steps:** Project submission.