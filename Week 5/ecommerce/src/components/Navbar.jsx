import {NavLink} from 'react-router-dom';
// import './Navbar.css';

const Navbar = () => {
    return (
        <nav id = 'nav'>
            <NavLink 
              to="/" 
            >
            Home
            </NavLink>

            <NavLink 
              to="/products"
            >
              Products
            </NavLink>
            
            <NavLink 
              to="/cart" 
            >
            View Cart
            </NavLink>
        </nav>
    )
}

export default Navbar;