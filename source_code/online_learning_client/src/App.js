import { useRoutes } from 'react-router-dom';
import './App.css';
import ROUTES from './components/Routes/Routes';
function App() {
  const ALLL_ROUTES = useRoutes(ROUTES);
  return (
    <>
      {ALLL_ROUTES}
    </>
    
  );
}

export default App;
