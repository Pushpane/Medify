import { ISignup } from "../auth/signup";
import { IMedToStore } from "./medToStore";
import { IStore } from "./store";

export interface ICart {
    cartId: number;
    userId: ISignup;
    medicineToStoreId: IMedToStore;
    quantity: number;
    cost: number;
}