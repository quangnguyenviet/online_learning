import "./About.scss";

export default function About() {
    return (
        <div className="about-page">
            <h2>About Us</h2>
            <section className="intro">
                <p>
                    Welcome to <strong>Online Learning</strong>, your trusted platform for accessible, high-quality education.
                    Whether you're looking to upgrade your skills, explore new fields, or deepen your knowledge, we're here to help you succeed.
                </p>
            </section>

            <section className="mission">
                <h3>Our Mission</h3>
                <p>
                    We aim to make learning available to everyone, everywhere. Our courses are designed by industry professionals
                    to help students, professionals, and lifelong learners achieve their personal and career goals.
                </p>
            </section>

            <section className="why-us">
                <h3>Why Choose Us?</h3>
                <ul>
                    <li>🎓 Expert Instructors</li>
                    <li>📚 Wide Range of Courses</li>
                    <li>🌍 Learn Anytime, Anywhere</li>
                    <li>💼 Career-Focused Learning</li>
                </ul>
            </section>
        </div>
    );
}
