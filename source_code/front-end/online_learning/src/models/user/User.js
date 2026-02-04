export class User {
    constructor({ id, firstName, lastName, 
        email, phoneNumber, dob, gender, role }) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.gender = gender;
        this.role = role;
    }
}