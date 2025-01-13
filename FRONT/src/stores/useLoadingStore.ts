import { create } from 'zustand';

type LoadingState = {
  isLoading: boolean;
  setIsLoading: (state: boolean) => void;
};

const useLoadingStore = create<LoadingState>((set) => ({
  isLoading: false,
  setIsLoading: (state) => set({ isLoading: state }),
}));

export default useLoadingStore;
