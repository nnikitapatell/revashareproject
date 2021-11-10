import { User } from "./user";

export class Comment {
    public id!: number;
    public author!: User;
    public description!: string;
    public submittedAt!: Date;
    constructor() {}
}
