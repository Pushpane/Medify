import { IMedicine } from "./medicine";
import { IStore } from "./store";

export interface IMedToStore {
    medicineToStoreId: number;
    storeId: IStore;
    medicineId: IMedicine;
    available: boolean;
}