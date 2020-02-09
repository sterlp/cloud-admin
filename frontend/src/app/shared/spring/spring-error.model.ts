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
export function isBindingError(object: any): object is BindingError {
    return object && object.field && object.defaultMessage && object.objectName;
}

// tslint:disable: curly
// tslint:disable: variable-name
export class SpringErrorWrapper {

    private _error: SpringErrorResult;
    private _validationErrors = new Map<string, BindingError>();

    get hasValidationErrors() {
        return this._validationErrors.size > 0;
    }

    public static of(springError: SpringErrorResult | any): SpringErrorWrapper {
        const result = new SpringErrorWrapper();
        result.init(springError);
        return result;
    }
    init(springError: SpringErrorResult | any) {
        this.clear();
        this._error = springError;
        if (this._error && this._error.status > 399 && Array.isArray(this._error.errors)) {
            this._error.errors.forEach(error => {
                if (isBindingError(error)) {
                    this._validationErrors.set(error.field, error);
                }
            });
        }
    }
    clear() {
        this._error = null;
        this._validationErrors.clear();
    }
    getBindingError(field: string) {
        return this._validationErrors.get(field);
    }
    hasBindingError(field: string) {
        return this._validationErrors.get(field) != null;
    }
}
