# Plant Donation System

## Overview
This project is a Java-based application that includes functionalities such as user registration, login, session management, and an admin page for database interaction. It uses AES encryption for securing passwords and connects to a MySQL database.

![Project Overview](./src/assets/home%20screen.png)

## Features
1. **Login Functionality**:
    - Users can log in using a username and password.
    - Passwords are encrypted and decrypted for security.
    - Successful login redirects the user to the home page.
    - Invalid credentials prompt an error message.

2. **Session Management**:
    - The logged-in user's ID is stored in a session utility.
    - The session can be cleared upon logout.

3. **Admin Page**:
    - Displays tables from the selected database in a tabbed pane.
    - Each table is shown only once, avoiding duplicates.
    - Tables are displayed with custom fonts and row heights.
    - Columns have predefined widths.

4. **Logout Functionality**:
    - Users can log out, which clears the session and redirects to the login page.
    - Logout operation is performed in a separate thread to keep the UI responsive.

5. **Navigation**:
    - Admin page includes a button to redirect to the home page.
    - Window listener redirects to the home page when the admin page is closed.

6. **Custom Look and Feel**:
    - Custom fonts and colors are set for various UI components like tabbed panes and tables.

7. **Error Handling**:
    - Errors during login, data loading, and password decryption are logged and displayed to the user.

8. **Database Interaction**:
    - Connects to a MySQL database to fetch user credentials and table data.
    - Uses `PreparedStatement` to prevent SQL injection.

9. **User Registration**:
    - Provides a link to navigate to the registration page from the login page.
    - Validates mobile number, email, and password format.

10. **Responsive UI**:
    - UI components are added and updated on the Event Dispatch Thread (EDT) to ensure responsiveness.

## Installation
1. **Clone the repository**:
   ```sh
   git clone https://github.com/tanishqmudaliar/Plant-Donation-System.git
   cd Plant-Donation-System
   ```