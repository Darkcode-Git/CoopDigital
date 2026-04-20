import { createSlice, type PayloadAction } from '@reduxjs/toolkit';
import type { Cuenta, Socio } from '@coopdigital/shared';

interface CatalogState {
  socios: Socio[];
  cuentas: Cuenta[];
}

const initialState: CatalogState = {
  socios: [],
  cuentas: [],
};

const catalogSlice = createSlice({
  name: 'catalog',
  initialState,
  reducers: {
    setSocios(state, action: PayloadAction<Socio[]>) {
      state.socios = action.payload;
    },
    setCuentas(state, action: PayloadAction<Cuenta[]>) {
      state.cuentas = action.payload;
    },
  },
});

export const { setSocios, setCuentas } = catalogSlice.actions;
export default catalogSlice.reducer;
