import { IStore } from "./store";

export interface IAddress {
    addressId: number;
    address1: String;
    address2: String;
    city: String;
    state: String;
    pincode: String;
    storeId: IStore;
}