# Trello in Spring Boot

This project is a web application similar to Trello, allowing administrators to manage columns and tasks. Admins can add columns and tasks, set deadlines, assign developers, and perform various management actions. Developers (other users) have limited capabilities, such as adding/removing comments and attachments to tasks. The application also features two types of reports: one showing task completion statistics for each developer and another displaying task counts for each column. Additionally, there's a "My Tasks" filter page that displays tasks assigned to the logged-in user. Links for reports and filters are conveniently placed in the navbar.

## Functions

- Admin can add columns and tasks, set deadlines, assign developers, and perform various management actions.
- Developers can add/remove comments and attachments to tasks.
- Two types of reports: one for task completion statistics per developer and another for task counts per column.
- "My Tasks" filter page displays tasks assigned to the logged-in user.

## Design Explanations

- Columns can be added via an input field named "Column Name" and the "Add New Column" button. A checkbox marks a column as finished, automatically marking tasks moved to it as finished. Tasks with deadlines display a clock icon, with red indicating overdue and green indicating completed on time.

## Technologies Used

- Spring Boot (web, JPA, Security, Thymeleaf, MVC)
- PostgreSQL
- HTML
- CSS
- Bootstrap (CSS and JS)
- Faker (for generating test data)
- SQL
- Docker (for deployment)

## Usage

1. Install Docker on your device.
2. Download the [docker-compose file](docker-compose.yml).
3. Open a terminal in the directory containing the docker-compose file.
4. Run the command `docker compose up -d`.
5. Ensure that ports 8080 and 5433 are open on your device.
6. Open [localhost:8080](http://localhost:8080) in your browser to access the login page.
7. Four users will be created automatically: "jason" (admin), "benjamin",
        "isabella", and "david". All users have the password "123".

## Project Screenshots

- Login page:
- <img src="/Project%20Screenshots/login_page.png" style="width:600px;">
- Main page:
- <img src="/Project%20Screenshots/main_page.png" style="width:600px;">
- Edit task page:
- <img src="/Project%20Screenshots/edit_task_page.png" style="width:600px;">
- Developers report page:
- <img src="/Project%20Screenshots/developers_report_page.png" style="width:600px;">
- Columns report page:
- <img src="/Project%20Screenshots/columns_report_page.png" style="width:600px;">
