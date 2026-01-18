import sqlite3

db_path = r"c:\Users\HP\Desktop\min project v2\gestion_absence.db"

print("=" * 70)
print("Debugging Absence List Issue")
print("=" * 70)

conn = sqlite3.connect(db_path)
cursor = conn.cursor()

# 1. Check if tables exist
print("\n1. Checking database tables...")
cursor.execute("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name")
tables = [row[0] for row in cursor.fetchall()]
print(f"   Tables: {tables}")

required = ['absence_table', 'student_table', 'subject_table']
missing = [t for t in required if t not in tables]
if missing:
    print(f"   ❌ MISSING TABLES: {missing}")
    print("   → Run: python init_database.py")
    conn.close()
    exit(1)

# 2. Check table contents
print("\n2. Checking table row counts...")
for table in ['student_table', 'subject_table', 'absence_table']:
    cursor.execute(f"SELECT COUNT(*) FROM {table}")
    count = cursor.fetchone()[0]
    status = "✓" if count > 0 else "⚠"
    print(f"   {status} {table:20s}: {count} rows")

# 3. Test the JOIN query
print("\n3. Testing AbsenceDAO.getAllAbsences() query...")
sql = """
SELECT a.*, s.first_name, s.last_name, sub.subject_name, c.class_level
FROM absence_table a 
JOIN student_table s ON a.id_student = s.id_student 
JOIN subject_table sub ON a.id_subject = sub.id_subject
JOIN class_table c ON s.id_class = c.id_class
"""

try:
    cursor.execute(sql)
    results = cursor.fetchall()
    print(f"   ✓ Query successful: {len(results)} records")
    
    if len(results) == 0:
        print("\n   ⚠ No absence records found!")
        print("   Reasons:")
        print("   - No absences have been recorded yet (normal)")
        print("   - Database was just initialized")
        print("\n   To test, you can:")
        print("   1. Login as a teacher")
        print("   2. Go to 'Record Absence' tab")
        print("   3. Select a class, subject, and mark students absent")
    else:
        print("\n4. Sample absence records:")
        for row in results[:3]:
            print(f"   - Student: {row[5]} {row[6]}, Subject: {row[7]}")
            
except sqlite3.Error as e:
    print(f"   ❌ Query failed: {e}")

# 4. Check for data integrity issues
print("\n5. Checking for orphaned records...")
cursor.execute("""
    SELECT COUNT(*) FROM absence_table a
    WHERE NOT EXISTS (SELECT 1 FROM student_table s WHERE s.id_student = a.id_student)
""")
orphaned_students = cursor.fetchone()[0]

cursor.execute("""
    SELECT COUNT(*) FROM absence_table a
    WHERE NOT EXISTS (SELECT 1 FROM subject_table s WHERE s.id_subject = a.id_subject)
""")
orphaned_subjects = cursor.fetchone()[0]

if orphaned_students > 0 or orphaned_subjects > 0:
    print(f"   ⚠ Found {orphaned_students} orphaned student refs, {orphaned_subjects} orphaned subject refs")
else:
    print(f"   ✓ No orphaned records")

conn.close()

print("\n" + "=" * 70)
print("Diagnosis complete")
print("=" * 70)
