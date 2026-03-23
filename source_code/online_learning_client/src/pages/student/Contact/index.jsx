import "./Contact.scss";

export default function Contact() {
    return (
        <div className="contact-page">
            <h2>Contact Us</h2>
            <p>If you have any questions or inquiries, feel free to send us a message using the form below.</p>

            <form
                className="contact-form"
                action="https://docs.google.com/forms/u/0/d/e/1FAIpQLScWx0LEkT4-oYXDfr1lSUPUhci9k-7HWsfkhczv7l5tfcUrrA/formResponse?edit2=2_ABaOnuezQqUvQ32DgI4uEeUiY0VGy5Tuidq5pmFW5LwAFOZcp0rwlxeTZWRjb0OrCQ"
                method="POST"
                target="_blank"
            >
                <div className="form-group">
                    <label htmlFor="name">Your Name</label>
                    <input
                        type="text"
                        id="name"
                        name="entry.2005620554"  // Replace with actual entry ID
                        placeholder="Enter your name"
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="email">Your Email</label>
                    <input
                        type="email"
                        id="email"
                        name="entry.656689398"  // Replace with actual entry ID
                        placeholder="Enter your email"
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="message">Your Message</label>
                    <textarea
                        id="message"
                        name="entry.1010609988"  // Replace with actual entry ID
                        rows="5"
                        placeholder="Type your message..."
                        required
                    ></textarea>
                </div>

                <button type="submit">Send Message</button>
            </form>

        </div>
    );
}
