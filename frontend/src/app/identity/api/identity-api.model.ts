export interface IdentityList {
    identities: Identity[];
}
export interface Identity {
    /** ID of the identity */
    id: number;
    /**
     * The unique name of this identity in the system
     */
    name: string;

    firstName?: string;
    lastName?: string;

    email?: string;

    accountStrategy: AccountGenerationStrategy;
}

export enum AccountGenerationStrategy {
    SAME_AS_IDENTITY_ID
}
