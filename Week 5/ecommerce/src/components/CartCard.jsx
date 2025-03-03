import { useCart } from "../context/CartContext.jsx";
import cheetos from "../assets/cheetos.png";
import lays from "../assets/lays.png";
import doritos from "../assets/doritos.png";


const idcheck = (id) => {
    if (id === 1) return "Flamin' Hot Cheetos";
    if (id === 2) return "Lays Classic Salted";
    if (id === 3) return "Doritos Cool Ranch";
    return "";
};

const assetcheck = (id) => {
    if (id === 1) return cheetos;
    if (id === 2) return lays;
    if (id === 3) return doritos;
    return "";
};

const pricecheck = (id) => {
    if(id==1) return 3;
    if(id==2) return 5;
    if(id==3) return 7;
}

const pricedisplay = ({qty}, price) => qty*price;

const CartCard = ({ id, qty }) => {
    const { dispatch } = useCart();

    return (
        <div id="cartitem">
            <div>
                <img className="image" src={assetcheck(id)} alt={idcheck(id)} />
            </div>
            <div id="cartdetails">
                <p>{idcheck(id)}</p>
                <p>Qty: x{qty}</p>
                <p>Price: ${pricedisplay({qty}, pricecheck(id))}</p>
                <button
                    onClick={() => {
                        console.log(`Decrement button clicked for ID: ${id}`);
                        dispatch({ type: "REMOVE_FROM_CART", id });
                    }}
                >
                    Remove from Cart
                </button>
            </div>
        </div>
    );
};

export default CartCard;
