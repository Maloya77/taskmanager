# Advanced Task Manager (Spring Boot Backend)

An advanced backend system for personal and collaborative task management. Built with Java, Spring Boot, and MySQL.

---

## ✅ Features Implemented

- 🔐 **Task Locking**
  - Tasks become locked when nearing deadlines.
  - Locked tasks cannot be updated.

- 🧠 **Reminder System**
  - Auto-reminders for tasks due within 3 days.
  - Custom one-time reminders.
  - Emails sent using integrated mail service.

- 🔄 **CRUD Functionality**
  - Create, Read, Update, Delete tasks.
  - Includes validation and error handling.

- 🗃️ **Database**
  - MySQL
  - Task table with fields like:
    - `title`, `description`, `status`, `isLocked`
    - `dueDate`, `priority`, `assignedTo`
    - `attachments`, `customReminderTime`, `lastReminderSent`

- 🧪 **Testing**
  - Unit tests for:
    - Task locking logic.
    - Reminder system.
  - Real emails verified (✅).

---

## 🧩 Roadmap (Planned Features)

| Feature                  | Status         |
|--------------------------|----------------|
| File Uploads             | 🔜 Next         |
| Time Tracking            | ⏳ Not Started  |
| Subtasks                 | ⏳ Not Started  |
| Angular Frontend         | ⏳ Not Started  |
| Security (JWT + Login)   | ⏳ Not Started  |
| Swagger Documentation    | ⏳ Not Started  |
| Logging (SLF4J/Logback)  | ⏳ Not Started  |

---

## 🧠 Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- JavaMailSender
- JUnit 5
- Maven

---

## 🚀 Getting Started (Local Setup)

1. **Clone the repo**
   ```bash
   git clone https://github.com/Maloya77/task-manager.git
   cd task-manager
