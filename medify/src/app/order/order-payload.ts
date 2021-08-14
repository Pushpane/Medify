import { ISignup } from "../auth/signup";
import { IAddress } from "../dashboard/address";
import { IMedToStore } from "../dashboard/medToStore";

export interface IOrderPayload {
    addressId: IAddress;
    cost: number;
    medicineToStoreId: IMedToStore;
    orderId: number;
    orderStatus: String;
    quantity: number;
    userId: ISignup;
}
