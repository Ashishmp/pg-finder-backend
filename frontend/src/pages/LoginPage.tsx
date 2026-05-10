import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Mail, Lock, ArrowRight, AlertCircle, ShieldCheck } from 'lucide-react';

const LoginPage: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await login({ email, password });
      navigate('/');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Invalid email or password');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="pt-24 pb-12 min-h-screen flex items-center justify-center bg-gradient px-4 relative overflow-hidden">
      {/* Decorative Glows */}
      <div className="absolute top-1/4 -left-20 w-80 h-80 bg-primary/10 blur-[100px] rounded-full"></div>
      <div className="absolute bottom-1/4 -right-20 w-80 h-80 bg-accent/10 blur-[100px] rounded-full"></div>

      <div className="glass w-full max-w-[480px] p-12 rounded-[40px] animate-fade-in border-glass-border shadow-2xl relative z-10">
        <div className="text-center mb-12">
          <div className="w-16 h-16 bg-primary/10 rounded-2xl flex items-center justify-center mx-auto mb-6">
            <ShieldCheck size={32} className="text-primary" />
          </div>
          <h1 className="text-4xl font-bold mb-3 tracking-tight">Welcome Back</h1>
          <p className="text-text-muted font-medium">Sign in to your account to continue</p>
        </div>

        {error && (
          <div className="mb-8 p-4 bg-red-500/10 border border-red-500/20 rounded-2xl flex items-center gap-3 text-red-400 text-sm font-medium animate-fade-in">
            <AlertCircle size={18} />
            <span>{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-8">
          <div className="space-y-2.5">
            <label className="text-sm font-bold text-text-muted ml-1 uppercase tracking-wider">Email Address</label>
            <div className="relative">
              <Mail className="absolute left-5 top-1/2 -translate-y-1/2 text-primary/50" size={20} />
              <input 
                type="email" 
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full bg-surface-light/30 border border-border rounded-2xl py-4 pl-14 pr-5 focus:border-primary focus:ring-1 focus:ring-primary transition-all text-text placeholder:text-text-muted/30 font-medium"
                placeholder="name@example.com"
              />
            </div>
          </div>

          <div className="space-y-2.5">
            <label className="text-sm font-bold text-text-muted ml-1 uppercase tracking-wider">Password</label>
            <div className="relative">
              <Lock className="absolute left-5 top-1/2 -translate-y-1/2 text-primary/50" size={20} />
              <input 
                type="password" 
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full bg-surface-light/30 border border-border rounded-2xl py-4 pl-14 pr-5 focus:border-primary focus:ring-1 focus:ring-primary transition-all text-text placeholder:text-text-muted/30 font-medium"
                placeholder="••••••••"
              />
            </div>
          </div>

          <div className="flex items-center justify-between text-sm">
            <label className="flex items-center gap-2.5 cursor-pointer group">
              <input type="checkbox" className="w-5 h-5 rounded-lg border-border bg-surface text-primary focus:ring-primary transition-colors" />
              <span className="text-text-muted group-hover:text-text transition-colors font-medium">Remember me</span>
            </label>
            <a href="#" className="text-primary font-bold hover:text-accent transition-colors">Forgot password?</a>
          </div>

          <button 
            type="submit" 
            disabled={loading}
            className="btn btn-primary w-full py-4 rounded-2xl flex items-center justify-center gap-3 text-lg shadow-xl shadow-primary/20"
          >
            {loading ? 'Signing in...' : 'Sign In to Account'}
            {!loading && <ArrowRight size={20} />}
          </button>
        </form>

        <p className="mt-12 text-center text-text-muted font-medium">
          Don't have an account? <Link to="/register" className="text-primary font-bold hover:text-accent transition-colors ml-1">Register now</Link>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
