# Email System - Quick Reference Card

## 🚀 Quick Start

### 1️⃣ Setup Database (Run ONCE)
```cmd
python add_email_table.py
```

### 2️⃣ Rebuild Project
- In your IDE: Clean and Build
- Or compile manually if needed

### 3️⃣ Run Application
```cmd
java -cp "bin;target/classes" com.miniproject.Main
```

---

## 👤 User Accounts for Testing

| Role    | Username | Password |
|---------|----------|----------|
| Admin   | admin    | admin    |
| Student | ali      | 123      |
| Student | sarra    | 123      |

---

## 📧 Admin Features

### Send Email Tab
1. Select Class → Select Student
2. Enter Subject and Message
3. Click "Send Email"
4. ✅ Confirmation appears

### View Sent Emails Tab
1. Browse email list
2. Click email to view details
3. Check read status
4. Delete if needed

---

## 📬 Student Features

### My Emails Tab
1. Check unread count (red badge)
2. Browse email list (NEW = unread)
3. Click email to read
4. Mark as read when done
5. Refresh to update

---

## 🗄️ Database Table

**admin_email_table**
- `id_email` - Unique ID
- `id_admin` - Who sent it
- `id_student` - Who receives it
- `subject` - Email subject
- `body` - Email message
- `sent_at` - Timestamp
- `is_read` - 0=unread, 1=read

---

## 📁 New Files

### Core Files
- `Email.java` - Model
- `EmailDAO.java` - Database operations
- `SendEmailPanel.java` - Admin send UI
- `StudentEmailPanel.java` - Student view UI
- `ViewEmailsPanel.java` - Admin view UI

### Setup Files
- `add_email_table.py` - Python setup
- `add_email_table_migration.sql` - SQL script
- `SimpleEmailTableSetup.java` - Java setup

### Documentation
- `EMAIL_FUNCTIONALITY_GUIDE.md` - Full guide
- `EMAIL_SETUP_README.md` - Setup instructions
- `IMPLEMENTATION_SUMMARY.md` - Complete summary

---

## 🔧 Troubleshooting

### Email table doesn't exist?
```cmd
python add_email_table.py
```

### Can't see email tabs?
- Check you're logged in with correct role
- Admin sees: Send Email, View Sent Emails
- Student sees: My Emails

### Compilation errors?
- Rebuild project in IDE
- Check all new files are in correct folders

---

## ✨ Key Features

✅ Send emails to individual students  
✅ View all sent emails  
✅ Track read/unread status  
✅ Student inbox with unread count  
✅ Mark emails as read  
✅ Delete emails  
✅ Sample data included  
✅ Professional UI design  

---

## 📊 Sample Data

2 sample emails included:
1. To Mohamed - Java absence warning
2. To Sarra - Database absences alert

---

## 🎯 Common Tasks

**Send an email:**
Admin → Send Email tab → Select class/student → Write → Send

**Check emails:**
Student → My Emails tab → Click email → Read → Mark as read

**View sent emails:**
Admin → View Sent Emails tab → Browse list → Click to view

**Delete an email:**
Admin → View Sent Emails tab → Select → Delete Selected

---

## 💡 Tips

- Use clear, descriptive subjects
- Students should check emails regularly
- Unread count shows new messages
- Emails sorted by date (newest first)
- Sample emails help test the system

---

## 📞 Need Help?

See detailed documentation:
- `EMAIL_FUNCTIONALITY_GUIDE.md`
- `EMAIL_SETUP_README.md`
- `IMPLEMENTATION_SUMMARY.md`

All code is well-commented!

---

**Version:** 1.0  
**Date:** January 2026  
**Status:** ✅ Production Ready
