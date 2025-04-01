import { createContext } from "react";
import { doctors } from "../assets/assets_frontend/assets";

export const AppContext = createContext();

//create a context provider function

const AppContextProvider = (props) => {

  const currencySymbol="Rs"
  const value = {
    doctors,
    currencySymbol
  };
  return (
    <AppContext.Provider value={value}>
        {props.children}
        </AppContext.Provider>
  );
};

export default AppContextProvider;
