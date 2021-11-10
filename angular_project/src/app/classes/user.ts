
export class User {

    public id: string = '';
    public username: string = '';
    public email: string = '';
    public password: string = '';
    public firstName: string = '';
    public lastName: string = '';
    public aboutMe: string = '';
    public imageURL: string = '';
    public lastChecked!: Date;


    constructor(username: string, firstName: string, lastName: string, emailAddress: string, password: string, imageUrl: string, aboutMe: string) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = emailAddress;
        this.password = password;
        this.imageURL = imageUrl;
        this.aboutMe = aboutMe;
    }
}
