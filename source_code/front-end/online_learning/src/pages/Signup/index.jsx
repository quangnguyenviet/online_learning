import './Signup.scss';

export default function Signup() {

    const handleSubmit = (e) => {
        console.log(e.target);
        e.preventDefault();
        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData.entries());
        fetch('http://localhost:8080/online_learning/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === 1000) {
                alert('User created successfully!');
            } else {
                alert('Error creating user: ' + data.message);
            }
        })
    }

    return (
        <>
            <form className="form-signup" onSubmit={handleSubmit}>

                <div className="content">
                    <h2>Signup</h2>
                    <div className="form-group">
                        <label htmlFor="username">Username</label>
                        <input type="text" className="form-control" id="username" placeholder="Enter username" name='username' />

                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email address</label>
                        <input type="email" className="form-control" id="email" placeholder="Enter email" name='email' />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input type="password" className="form-control" id="password" placeholder="Password" name='password'/>
                    </div>
                    <button type='submit' className='btn btn-primary'>Sign up</button>
                </div>

            </form>
        </>
    );
}