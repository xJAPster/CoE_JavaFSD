// import './Products.css'
import cheetos from '../assets/cheetos.png';
import lays from '../assets/lays.png'
import doritos from '../assets/doritos.png'
import ProductCard from "../components/ProductCard.jsx";

const Products = () => {
    return (
        <div id = "containerprod">
            <ProductCard id = {1} prod = {cheetos} prodname="Flamin' Hot Cheetos"/>
            <ProductCard id = {2} prod = {lays} prodname='Lays Classic Salted'/>
            <ProductCard id = {3} prod = {doritos} prodname='Doritos Cool Ranch'/>
        </div>
    )
}

export default Products;