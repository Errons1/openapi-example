import styles from "./App.module.scss"
import {Pets} from "./pets/Pets.tsx";

function App() {

    return (
        <div className={styles.appContainer}>
            <h1>Pet Store</h1>
            <Pets/>
        </div>
    )
}

export default App
