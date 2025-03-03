import { createContext, useContext, useReducer } from "react";

const CartContext = createContext();

const initialState = new Map([[1,0], [2,0], [3,0]]);

const cartReducer = (state, action) => {
  const newState = new Map(state);
  switch (action.type) {
    case "ADD_TO_CART":
      newState.set(action.id, (newState.get(action.id) || 0) + 1);
      console.log(action.id + "bumped to" + newState.get(action.id))
      return newState;
    case "REMOVE_FROM_CART":
      if (newState.has(action.id) && newState.get(action.id) > 0) {
        newState.set(action.id, newState.get(action.id) - 1);
        console.log(action.id + "bumped down to" + newState.get(action.id))
      }
      return newState;
    default:
      return state;
  }
};

export const CartProvider = ({ children }) => {
  const [state, dispatch] = useReducer(cartReducer, initialState);
  return (
    <CartContext.Provider value={{ state, dispatch }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => useContext(CartContext); 

export default CartContext;
