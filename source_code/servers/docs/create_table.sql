/* =========================
   DATABASE STRUCTURE
   ========================= */

/* ---------- CATEGORY ---------- */
CREATE TABLE category (
                          id INT AUTO_INCREMENT NOT NULL,
                          name VARCHAR(255) NOT NULL UNIQUE,
                          description VARCHAR(255),
                          CONSTRAINT pk_category PRIMARY KEY (id)
);

/* ---------- ROLE ---------- */
CREATE TABLE role (
                      name VARCHAR(255) NOT NULL,
                      description VARCHAR(255),
                      CONSTRAINT pk_role PRIMARY KEY (name)
);

/* ---------- USER ---------- */
CREATE TABLE user (
                      id VARCHAR(255) NOT NULL,
                      email VARCHAR(255),
                      password VARCHAR(255) NOT NULL,
                      first_name VARCHAR(255),
                      last_name VARCHAR(255),
                      dob DATETIME,
                      gender ENUM('MALE', 'FEMALE', 'OTHER') DEFAULT 'OTHER',
                      phone_number VARCHAR(255),
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      CONSTRAINT pk_user PRIMARY KEY (id),
                      CONSTRAINT uc_user_email UNIQUE (email)
);

/* ---------- USER_ROLE ---------- */
CREATE TABLE user_role (
                           user_id VARCHAR(255) NOT NULL,
                           role_name VARCHAR(255) NOT NULL,
                           CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_name),
                           CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES user (id),
                           CONSTRAINT fk_user_role_role FOREIGN KEY (role_name) REFERENCES role (name)
);

/* ---------- COURSE ---------- */
CREATE TABLE course (
                        id VARCHAR(255) NOT NULL,
                        title VARCHAR(255),
                        image_url VARCHAR(255),
                        published BIT(1),
                        instructor_id VARCHAR(255),
                        price DECIMAL(15,2),
                        discount INT,
                        long_desc TEXT,
                        short_desc VARCHAR(255),
                        category_id INT,
                        level VARCHAR(255),
                        created_at DATETIME,
                        CONSTRAINT pk_course PRIMARY KEY (id),
                        CONSTRAINT fk_course_category FOREIGN KEY (category_id) REFERENCES category (id),
                        CONSTRAINT fk_course_instructor FOREIGN KEY (instructor_id) REFERENCES user (id)
);

/* ---------- COURSE_REQUIREMENT ---------- */
CREATE TABLE course_requirement (
                                    id VARCHAR(255) NOT NULL,
                                    description VARCHAR(255),
                                    course_id VARCHAR(255),
                                    CONSTRAINT pk_course_requirement PRIMARY KEY (id),
                                    CONSTRAINT fk_course_requirement_course FOREIGN KEY (course_id) REFERENCES course (id)
);

/* ---------- OBJECTIVE ---------- */
CREATE TABLE objective (
                           id VARCHAR(255) NOT NULL,
                           description VARCHAR(255),
                           course_id VARCHAR(255),
                           CONSTRAINT pk_objective PRIMARY KEY (id),
                           CONSTRAINT fk_objective_course FOREIGN KEY (course_id) REFERENCES course (id)
);

/* ---------- LESSON ---------- */
CREATE TABLE lesson (
                        id VARCHAR(255) NOT NULL,
                        title VARCHAR(255),
                        lesson_key VARCHAR(255),
                        video_url VARCHAR(255),
                        description TEXT,
                        duration BIGINT NOT NULL,
                        course_id VARCHAR(255),
                        is_preview BOOLEAN NOT NULL DEFAULT FALSE,
                        created_at DATETIME,
                        CONSTRAINT pk_lesson PRIMARY KEY (id),
                        CONSTRAINT fk_lesson_course FOREIGN KEY (course_id) REFERENCES course (id)
);

/* ---------- REGISTER ---------- */
CREATE TABLE register (
                          id VARCHAR(255) NOT NULL,
                          student_id VARCHAR(255),
                          course_id VARCHAR(255),
                          price DECIMAL(15,2),
                          register_date DATETIME,
                          CONSTRAINT pk_register PRIMARY KEY (id),
                          CONSTRAINT fk_register_student FOREIGN KEY (student_id) REFERENCES user (id),
                          CONSTRAINT fk_register_course FOREIGN KEY (course_id) REFERENCES course (id)
);

/* ---------- INSTRUCTOR_STATISTIC ---------- */
CREATE TABLE instructor_statistic (
                                      id VARCHAR(255) NOT NULL,
                                      month INT NOT NULL,
                                      year INT NOT NULL,
                                      total_registrations INT NOT NULL,
                                      total_earnings DECIMAL(15,2) NOT NULL,
                                      instructor_id VARCHAR(255),
                                      CONSTRAINT pk_instructor_statistic PRIMARY KEY (id),
                                      CONSTRAINT fk_instructor_statistic_instructor FOREIGN KEY (instructor_id) REFERENCES user (id)
);

/* ---------- INSTRUCTOR_PAYMENT ---------- */
CREATE TABLE instructor_payment (
                                    id VARCHAR(255) NOT NULL,
                                    statistic_id VARCHAR(255) NOT NULL,
                                    paid_at DATETIME,
                                    CONSTRAINT pk_instructor_payment PRIMARY KEY (id),
                                    CONSTRAINT uc_instructor_payment_statistic UNIQUE (statistic_id),
                                    CONSTRAINT fk_instructor_payment_statistic FOREIGN KEY (statistic_id)
                                        REFERENCES instructor_statistic (id)
);

/* ---------- INSTRUCTOR_PROFILE ---------- */
CREATE TABLE instructor_profile (
                                    user_id VARCHAR(255) NOT NULL,
                                    bio TEXT,
                                    bank_name VARCHAR(255),
                                    account_number VARCHAR(255),
                                    account_name VARCHAR(255),
                                    CONSTRAINT pk_instructor_profile PRIMARY KEY (user_id),
                                    CONSTRAINT fk_instructor_profile_user FOREIGN KEY (user_id) REFERENCES user (id)
);

/* ---------- INVALID_TOKEN ---------- */
CREATE TABLE invalid_token (
                               id VARCHAR(255) NOT NULL,
                               expiration DATETIME,
                               CONSTRAINT pk_invalid_token PRIMARY KEY (id)
);

/* ---------- LESSON_PROGRESS ---------- */
CREATE TABLE lesson_progress (
                                 user_id VARCHAR(255) NOT NULL,
                                 lesson_id VARCHAR(255) NOT NULL,
                                 is_completed BOOLEAN NOT NULL DEFAULT TRUE,
                                 completed_at DATETIME DEFAULT CURRENT_TIMESTAMP,

                                 CONSTRAINT pk_lesson_progress PRIMARY KEY (user_id, lesson_id),

                                 CONSTRAINT fk_lesson_progress_user
                                     FOREIGN KEY (user_id) REFERENCES user(id),

                                 CONSTRAINT fk_lesson_progress_lesson
                                     FOREIGN KEY (lesson_id) REFERENCES lesson(id)
);
