import { create } from "zustand";

interface ToastState {
  message: string,
  duration: number,
  isVisible: boolean,
  setMessage: (message: string) => void
  setDuration: (duration: number) => void;
  setIsVisible: (isVisible: boolean) => void;
}

const useToastStore = create<ToastState>((set) => ({
  message: '',
  duration: 2500,
  isVisible: false,
  setMessage: (message: string) => set({ message }),
  setDuration: (duration: number) => set({ duration }),
  setIsVisible: (isVisible: boolean) => set({ isVisible })
}))

export default useToastStore;