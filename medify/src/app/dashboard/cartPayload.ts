import { IAddress } from "./address";

export interface ICartPayload {
    id: number;
    name: String;
    description:String;
    price: String;
    storeName: String;
    image: String;
    available: String;
    cart: boolean;
    address: IAddress;
}