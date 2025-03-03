// import './ProductCard.css'
import { useCart } from '../context/CartContext.jsx'

const pricecheck = (id) => {
    if(id==1) return 3;
    if(id==2) return 5;
    if(id==3) return 7;
}

const ProductCard = ({prod, prodname, id}) => {
    const {state, dispatch} = useCart();

    return(
        <div className ='product'>
            <img className='image' src = {prod}/>
            <div className = 'info'>

                <p>{prodname}</p>
                <div className = 'buyoptions'>
                    <p>Price : ${pricecheck(id)}</p>
                    <button
                    onClick={()=>{
                        console.log(`Increment button clicked for ID: ${id}`);
                        dispatch({ type: "ADD_TO_CART", id })}}
                    >Add to Cart</button>
                </div>

            </div>
        </div>
    )
}

export default ProductCard;