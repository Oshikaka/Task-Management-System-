# My Personal Project

## Project Name: Ninja - A to-do list application

   - Tasks that each contains: 
     - Tag
     - Description
     - Due date
     - Estimated completion time

   - Things can do to to-do list:
     - Add
     - Remove
     - Clear
     - Search tasks by tag
     - Search tasks by dueDay period
     - Review all tasks
     - Save to-do list to file
     - Load to-do list from file
     

<h3>Who will use it?</h3>
- Anyone who have tasks to do and want to use tool to keep tracking their tasks.
- Anyone who wants to focus on completing a task within its estimated completion time.

<h3>Why is this project of interest to me?</h3>
I really want to use a todo list that contains all these functions that I described above. 
Be a Ninja, estimate the completion time of a task and do it on time. Never look back what you have done,
just remove it when you finish the task.
However, I could not find one exist todo application with these functions. It is a good idea 
to give you work an estimate completion time in order to manage your task better.

<h3>User Stories:</h3>
- As a user, I want to be able to add a task to my todo list.
- As a user, I want to be able to remove/delete a task to my todo list.
- As a user, I want to be able to search tasks on my todo list by tag.
- As a user, I want to be able to search tasks on my todo list by dueDay period.
- As a user, I want to be able to view the list of tasks on my todo list.

- As a user, I want to be able to save my todo list to file.
- As a user, I want to be able to load my todo list from file.
- As a user, I want to be able to be remained before I quit the app that if I want to save my todolist or not.

<h3>Phase 4: Task 2</h3>

A representative sample of the events that occur when this program runs:

Wed Nov 24 23:02:37 PST 2021

Task was added to todo list.

Wed Nov 24 23:02:40 PST 2021

Task was added to todo list.

Wed Nov 24 23:02:42 PST 2021

Task was removed to todo list.

For Clear method, there is no log for "The todo list has been cleared" since I implemented this method at
ui package, and we don't allow calling EventLog method outside the model package.


<h3>Phase 4: Task 3</h3>

If I have more time I will:
- make my project more robust, some implementations didn't use exception, for example for data addition, there
is no exception to check if the input is in the correct form.
- improve the cohesion and coupling of my project.
- use more abstract class and method to eliminate the duplication of some code to make my code more precise.
