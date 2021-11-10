import { User } from "./user";

export class Post {
    public id!: number;
    public author!: User;
    public description!: string;
    public imageUrl1!: string;
    public imageUrl2!: string;
    public imageUrl3!: string;
    public imageUrl4!: string;
    public youTubeUrl!: string;
    public submittedAt!: Date;
    constructor(){}
}
