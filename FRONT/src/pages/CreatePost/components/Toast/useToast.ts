import { useEffect, useState } from "react";
import useToastStore from "../../../../stores/useToastStore";

function useToast() {
  const { message, duration, isVisible, setIsVisible } = useToastStore();
  const [shouldRender, setShouldRender] = useState(false);

  const hideToast = () => {
    setIsVisible(false);
    setTimeout(() => setShouldRender(false), 300);
  }

  useEffect(() => {
    if (!isVisible) return;

    setShouldRender(true);

    const timer = setTimeout(hideToast, duration);
    return () => clearTimeout(timer);
  }, [isVisible, duration]);

  return {
    isVisible,
    message,
    shouldRender,
    hideToast,
  }
}

export default useToast;