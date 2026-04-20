import { createSlice, type PayloadAction } from '@reduxjs/toolkit';

interface SessionUser {
  id: string;
  email: string;
  role: 'SOCIO' | 'ADMIN';
}

interface SessionState {
  token: string | null;
  user: SessionUser | null;
}

const initialState: SessionState = {
  token: null,
  user: null,
};

const sessionSlice = createSlice({
  name: 'session',
  initialState,
  reducers: {
    setSession(state, action: PayloadAction<SessionState>) {
      state.token = action.payload.token;
      state.user = action.payload.user;
    },
    clearSession(state) {
      state.token = null;
      state.user = null;
    },
  },
});

export const { setSession, clearSession } = sessionSlice.actions;
export default sessionSlice.reducer;
