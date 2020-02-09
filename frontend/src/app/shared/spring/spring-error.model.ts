import { isArray } from 'util';

export interface SpringErrorResult {
    timestamp: string;
    status: number;
    error: string;
    errors: any;
    message: string;
    path: string;
}

/**
 * Spring representation of a validation constrain error
 */
export interface BindingError {
    codes?: Array<string>;
    defaultMessage: string;
    objectName: string;
    field: string;
    rejectedValue?: any;
    bindingFailure?: boolean;
    code?: string;
}
/**
 * We consider everything a BindingError if it has field, defaultMessage and objectName.
 */
export function isBindingError(object: any): object is any[] {
    return object && object.field && object.defaultMessage && object.objectName;
}

export class SpringErrorWrapper {

    private _error: SpringErrorResult;
    private _validationErrors: Map<string, BindingError>;

    constructor(springError: SpringErrorResult | any) {
        this._error = springError;
        if (this._error && this._error.status > 399 && isArray(this._error.errors)) {
            this._error.errors.forEach(error => {

            });
        }
    }

    public static isBindingError
}
