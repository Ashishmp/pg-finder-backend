import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Home, User, LogOut, LayoutDashboard, Search } from 'lucide-react';

const Navbar: React.FC = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <nav className="glass fixed top-6 left-1/2 -translate-x-1/2 z-[100] w-[95%] max-w-7xl rounded-2xl py-3 px-6 animate-fade-in border-glass-border">
      <div className="flex items-center justify-between">
        <Link to="/" className="text-2xl font-bold text-gradient flex items-center gap-2.5 group">
          <div className="w-10 h-10 bg-gradient-to-br from-primary to-accent rounded-xl flex items-center justify-center group-hover:rotate-6 transition-transform">
            <Home size={22} className="text-white" />
          </div>
          <span className="tracking-tight">PG Finder</span>
        </Link>

        <div className="hidden md:flex items-center gap-8">
          <Link to="/" className="text-sm font-semibold hover:text-primary transition-colors flex items-center gap-2">
            <Search size={18} className="text-primary/70" />
            <span>Find PG</span>
          </Link>

          {user ? (
            <>
              {user.role === 'OWNER' && (
                <Link to="/dashboard" className="text-sm font-semibold hover:text-primary transition-colors flex items-center gap-2">
                  <LayoutDashboard size={18} className="text-secondary/70" />
                  <span>Dashboard</span>
                </Link>
              )}
              <Link to="/profile" className="text-sm font-semibold hover:text-primary transition-colors flex items-center gap-2">
                <User size={18} className="text-accent/70" />
                <span>Profile</span>
              </Link>
              <div className="w-px h-6 bg-border mx-2"></div>
              <button 
                onClick={handleLogout}
                className="flex items-center gap-2 text-sm font-semibold text-text-muted hover:text-red-400 transition-colors"
              >
                <LogOut size={18} />
                <span>Sign Out</span>
              </button>
            </>
          ) : (
            <div className="flex items-center gap-6">
              <Link to="/login" className="text-sm font-semibold hover:text-primary transition-colors">Login</Link>
              <Link to="/register" className="btn btn-primary py-2.5 px-7 text-sm">Get Started</Link>
            </div>
          )}
        </div>
        
        {/* Mobile menu could be added here */}
      </div>
    </nav>
  );
};

export default Navbar;
