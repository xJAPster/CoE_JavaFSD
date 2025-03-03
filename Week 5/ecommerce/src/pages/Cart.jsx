// import "./Cart.css";
import { useCart } from "../context/CartContext.jsx";
import CartCard from "../components/CartCard.jsx";

const pricecheck = (id) => {
    if (id === 1) return 3;
    if (id === 2) return 5;
    if (id === 3) return 7;
    return 0;
};

const Cart = () => {
    const { state } = useCart();

    const totalCost = Array.from(state.entries()).reduce(
        (acc, [id, qty]) => acc + qty * pricecheck(id),
        0
    );

    return (
        <div id="containercart">
            {Array.from(state.entries()).map(([id, qty]) =>
                qty > 0 ? <CartCard key={id} id={id} qty={qty} /> : null
            )}
            <div id="totalcost">
                <h2>Total Cost: ${totalCost}</h2>
            </div>
        </div>
    );
};

export default Cart;
