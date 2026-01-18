# Email Functionality Guide

## Overview
The email system allows administrators to send messages to students and students to view their received emails. This feature is integrated into the Student Absence Management System.

## Features

### For Administrators

#### 1. Send Email Tab
- **Location**: Main Dashboard → "Send Email" tab (Admin only)
- **Features**:
  - Select class from dropdown
  - Select student from class
  - Enter email subject
  - Write email message body
  - Send button to deliver the email
  - Clear form button to reset fields

#### 2. View Sent Emails Tab
- **Location**: Main Dashboard → "View Sent Emails" tab (Admin only)
- **Features**:
  - View all sent emails in a table
  - See recipient, subject, date, and read status
  - Click on email to view full content
  - Delete emails
  - Refresh to update the list

### For Students

#### My Emails Tab
- **Location**: Main Dashboard → "My Emails" tab (Student only)
- **Features**:
  - View all received emails
  - See unread email count (displayed in red)
  - Email list showing status (NEW/Read), sender, subject, and date
  - Click on email to view full content
  - Mark emails as read
  - Refresh to update the list

## Database Schema

### admin_email_table
```sql
CREATE TABLE admin_email_table (
    id_email     INTEGER PRIMARY KEY AUTOINCREMENT,
    id_admin     INTEGER NOT NULL,
    id_student   INTEGER NOT NULL,
    subject      TEXT    NOT NULL,
    body         TEXT    NOT NULL,
    sent_at      TEXT    NOT NULL DEFAULT (datetime('now')),
    is_read      INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (id_admin) REFERENCES admin_table(id_admin),
    FOREIGN KEY (id_student) REFERENCES student_table(id_student)
);
```

## Implementation Details

### New Files Created

1. **Model**: `Email.java`
   - Represents email data
   - Contains fields: idEmail, idAdmin, idStudent, subject, body, sentAt, isRead
   - Additional display fields: adminName, studentName

2. **DAO**: `EmailDAO.java`
   - `sendEmail()` - Send email from admin to student
   - `getEmailsByAdmin()` - Get all emails sent by an admin
   - `getEmailsByStudent()` - Get all emails received by a student
   - `markAsRead()` - Mark an email as read
   - `getUnreadCount()` - Get count of unread emails for a student
   - `getAllEmails()` - Get all emails (for admin view)
   - `deleteEmail()` - Delete an email

3. **UI Panels**:
   - `SendEmailPanel.java` - Admin interface to compose and send emails
   - `StudentEmailPanel.java` - Student interface to view received emails
   - `ViewEmailsPanel.java` - Admin interface to view all sent emails

### Updated Files

1. **MainDashboard.java**
   - Added "Send Email" tab for admins
   - Added "View Sent Emails" tab for admins
   - Added "My Emails" tab for students

2. **database_setup_sqlite.sql**
   - Added admin_email_table creation
   - Added indexes for performance
   - Added sample email data

## Usage Instructions

### For Administrators

#### Sending an Email:
1. Log in as admin
2. Navigate to "Send Email" tab
3. Select the class from the dropdown
4. Select the student from the dropdown (filtered by class)
5. Enter the email subject
6. Write the email message
7. Click "Send Email"
8. A confirmation message will appear

#### Viewing Sent Emails:
1. Log in as admin
2. Navigate to "View Sent Emails" tab
3. Browse the list of sent emails
4. Click on any email to view its full content
5. Use "Delete Selected" to remove an email
6. Use "Refresh" to update the list

### For Students

#### Viewing Emails:
1. Log in as student
2. Navigate to "My Emails" tab
3. Check the unread count at the top right
4. Browse the email list (NEW emails are marked)
5. Click on any email to view its full content
6. Use "Mark as Read" to mark the selected email as read
7. Use "Refresh" to update the list

## Sample Data

The system includes sample emails:
- Email to Mohamed about Java Programming absence
- Email to Sarra about Database Systems multiple absences

## Technical Notes

- Emails are stored with timestamps using SQLite's datetime function
- The `is_read` field uses 0 (unread) and 1 (read) values
- Email content supports multi-line text
- The system uses JTable for email lists and JTextArea for content display
- Split panes provide a clean interface for viewing email lists and content

## Future Enhancements

Possible improvements:
- Email attachments
- Email templates for common messages
- Bulk email sending to entire classes
- Email search and filtering
- Email notifications/alerts
- Email reply functionality
- Rich text formatting
