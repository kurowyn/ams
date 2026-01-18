# Email Functionality - Setup and Usage

## 🎉 What's New?

Your Student Absence Management System now includes a complete email functionality that allows:
- **Administrators** to send emails to students
- **Students** to view and read their emails

## 📋 Quick Setup

### Step 1: Add Email Table to Database

Run **ONE** of the following methods:

#### Method A: Using Java Utility (Recommended)
```bash
# Compile and run the setup utility
javac -cp ".;lib/*" src/com/miniproject/database/SimpleEmailTableSetup.java
java -cp ".;lib/*;src" com.miniproject.database.SimpleEmailTableSetup
```

#### Method B: Using SQLite Command Line
```bash
sqlite3 gestion_absence.db < add_email_table_migration.sql
```

### Step 2: Compile the New Files

Compile all the new email-related files:
```bash
javac -cp ".;lib/*" src/com/miniproject/model/Email.java
javac -cp ".;lib/*" src/com/miniproject/dao/EmailDAO.java
javac -cp ".;lib/*" src/com/miniproject/ui/SendEmailPanel.java
javac -cp ".;lib/*" src/com/miniproject/ui/StudentEmailPanel.java
javac -cp ".;lib/*" src/com/miniproject/ui/ViewEmailsPanel.java
javac -cp ".;lib/*" src/com/miniproject/ui/MainDashboard.java
```

### Step 3: Run the Application

```bash
java -cp ".;lib/*;src" com.miniproject.Main
```

## 🎯 Features

### For Administrators

1. **Send Email Tab**
   - Select a class
   - Choose a student from that class
   - Write subject and message
   - Send email instantly

2. **View Sent Emails Tab**
   - See all emails you've sent
   - View email details
   - Check if students have read them
   - Delete emails if needed

### For Students

1. **My Emails Tab**
   - View all emails from administrators
   - See unread count in red
   - Read email content
   - Mark emails as read

## 📁 New Files Created

### Models
- `src/com/miniproject/model/Email.java` - Email data model

### DAO (Database Access)
- `src/com/miniproject/dao/EmailDAO.java` - Email database operations

### UI Panels
- `src/com/miniproject/ui/SendEmailPanel.java` - Admin sends emails
- `src/com/miniproject/ui/StudentEmailPanel.java` - Student views emails
- `src/com/miniproject/ui/ViewEmailsPanel.java` - Admin views sent emails

### Database
- `add_email_table_migration.sql` - SQL migration script
- `src/com/miniproject/database/SimpleEmailTableSetup.java` - Java setup utility

### Documentation
- `EMAIL_FUNCTIONALITY_GUIDE.md` - Detailed feature documentation
- `EMAIL_SETUP_README.md` - This file

## 📊 Database Schema

The email table structure:
```sql
admin_email_table
├── id_email (PRIMARY KEY)
├── id_admin (FOREIGN KEY → admin_table)
├── id_student (FOREIGN KEY → student_table)
├── subject
├── body
├── sent_at (timestamp)
└── is_read (0 or 1)
```

## 🧪 Testing

### Test as Admin:
1. Login with: `admin` / `admin`
2. Go to "Send Email" tab
3. Send a test email to a student
4. Check "View Sent Emails" to see it

### Test as Student:
1. Login with: `ali` / `123` (or `sarra` / `123`)
2. Go to "My Emails" tab
3. You should see sample emails
4. Click on an email to read it
5. Mark it as read

## 🔧 Troubleshooting

### Email table doesn't exist?
Run the SimpleEmailTableSetup utility:
```bash
java -cp ".;lib/*;src" com.miniproject.database.SimpleEmailTableSetup
```

### Compilation errors?
Make sure all dependencies are in the `lib` folder, especially `sqlite-jdbc.jar`

### Can't see email tabs?
- Admins: Check you're logged in as admin
- Students: Check you're logged in as a student account

## 📝 Sample Data

The system includes 2 sample emails:
1. Email to Mohamed about Java class absence
2. Email to Sarra about Database class absences

## 🚀 Next Steps

You can now:
- Send emails to students about their absences
- Students can check their emails regularly
- Track which emails have been read
- Delete old emails to keep the system clean

## 💡 Tips

- Use descriptive subjects for easy email identification
- Students should regularly check their "My Emails" tab
- Admins can track email read status
- The unread count helps students see new messages quickly

---

**Enjoy your new email functionality! 📧**
