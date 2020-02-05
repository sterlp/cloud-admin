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
    SAME_AS_IDENTITY_ID = 'SAME_AS_IDENTITY_ID'
}

export class IdentityModel {
  static readonly COLUMNS = [
    { id: 'name',       header: 'Login',        required: true,  type: 'text',  cell: (e: Identity) => e.name        },
    { id: 'firstName',  header: 'First Name',   required: false, type: 'text',  cell: (e: Identity) => e.firstName   },
    { id: 'lastName',   header: 'Last  Name',   required: false, type: 'text',  cell: (e: Identity) => e.lastName    },
    { id: 'email',      header: 'Email',        required: false, type: 'email', cell: (e: Identity) => e.email       }
  ];
}
