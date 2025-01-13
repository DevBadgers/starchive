import { create } from 'zustand';

interface AsideState {
  isAsideOpen: boolean;
  setIsAsideOpen: (isOpen: boolean) => void;
}

const useAsideStore = create<AsideState>((set) => ({
  isAsideOpen: false,
  setIsAsideOpen: (isOpen) => set({ isAsideOpen: isOpen }),
}));

export default useAsideStore;