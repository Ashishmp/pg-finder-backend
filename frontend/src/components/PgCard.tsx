import React from 'react';
import { Link } from 'react-router-dom';
import { MapPin, Star, Wifi, Shield, Coffee, ArrowRight } from 'lucide-react';

interface PgProps {
  id: number;
  pgName: string;
  pgCity: string;
  pgState: string;
  averageRating: number;
  totalReviews: number;
  description: string;
  amenities: { id: number; name: string }[];
}

const PgCard: React.FC<PgProps> = ({ 
  id, 
  pgName, 
  pgCity, 
  pgState, 
  averageRating, 
  totalReviews, 
  description,
  amenities 
}) => {
  const imageUrl = `https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?auto=format&fit=crop&q=80&w=800`;

  return (
    <Link to={`/pg/${id}`} className="group block glass rounded-lg overflow-hidden hover:transform hover:-translate-y-2 transition-all duration-500 glow-card border-glass-border">
      <div className="relative h-56 overflow-hidden">
        <img 
          src={imageUrl} 
          alt={pgName} 
          className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700"
        />
        <div className="absolute top-4 right-4 glass px-3 py-1.5 rounded-xl flex items-center gap-1.5 animate-fade-in">
          <Star size={16} className="text-yellow-400 fill-yellow-400" />
          <span className="text-sm font-bold">{averageRating.toFixed(1)}</span>
          <span className="text-[10px] text-text-muted font-medium">({totalReviews})</span>
        </div>
        <div className="absolute inset-0 bg-gradient-to-t from-background/80 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500 flex items-end p-6">
           <span className="text-white font-semibold flex items-center gap-2">
             View Details <ArrowRight size={16} />
           </span>
        </div>
      </div>
      
      <div className="p-6">
        <div className="flex justify-between items-start mb-3">
          <h3 className="text-xl font-bold group-hover:text-primary transition-colors line-clamp-1">{pgName}</h3>
        </div>
        
        <div className="flex items-center gap-1.5 text-text-muted text-sm mb-4">
          <MapPin size={14} className="text-primary/70" />
          <span>{pgCity}, {pgState}</span>
        </div>
        
        <p className="text-text-muted text-sm line-clamp-2 mb-6 leading-relaxed">
          {description || "A premium stay experience with all modern amenities and excellent security."}
        </p>

        <div className="flex items-center justify-between border-t border-border pt-5">
          <div className="flex -space-x-2.5">
            <div className="w-9 h-9 rounded-full bg-surface-light border-2 border-surface flex items-center justify-center group-hover:border-primary/30 transition-colors">
              <Wifi size={14} className="text-primary" />
            </div>
            <div className="w-9 h-9 rounded-full bg-surface-light border-2 border-surface flex items-center justify-center group-hover:border-secondary/30 transition-colors">
              <Shield size={14} className="text-secondary" />
            </div>
            <div className="w-9 h-9 rounded-full bg-surface-light border-2 border-surface flex items-center justify-center group-hover:border-accent/30 transition-colors">
              <Coffee size={14} className="text-accent" />
            </div>
          </div>
          <span className="text-xs font-semibold px-3 py-1 bg-primary/10 text-primary rounded-full">
            {amenities.length}+ Amenities
          </span>
        </div>
      </div>
    </Link>
  );
};

export default PgCard;
