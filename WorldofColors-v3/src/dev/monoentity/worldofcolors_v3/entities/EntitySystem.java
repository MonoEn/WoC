package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.items.Item;

public class EntitySystem {

	private Handy hand;
	private Player p;
	private Unknown u;
	private Temporary t;
	private ArrayList<Entity> entities;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private Comparator<Entity> renderer = new Comparator<Entity>(){
		@Override
		public int compare(Entity a, Entity b){
			if(a.getY() + a.getSize() < b.getY() + b.getSize())
				return -1;
			return 1;
		}
	};

	public EntitySystem(Handy hand, Unknown u1){
		this.hand = hand;
		u = u1;
		entities = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectile>();
		particles = new ArrayList<Particle>();
		add(u);
	}
	
	public void update(){
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			e.update();
			if(e.isRemoved()){
				entities.remove(e);
			}
		}
		for(int i = 0; i < projectiles.size(); i++){
			Projectile pr = projectiles.get(i);
			pr.update();
			if(pr.isRemoved()){
				projectiles.remove(pr);
			}
		}
		for(int i = 0; i < particles.size(); i++){
			Particle part = particles.get(i);
			part.update();
			if(part.isRemoved()){
				particles.remove(part);
			}
		}
		
		entities.sort(renderer);
	}
	
	public void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
		}
		for(Projectile pr : projectiles){
			pr.render(g);
		}
		for(Particle part : particles){
			part.render(g);
		}
		
	}
	
	public List<Entity> getTEntities(Entity e, int r){
		List<Entity> target = new ArrayList<Entity>();
		float ex = e.getX();
		float ey = e.getY();
		for(int i = 0; i < entities.size(); i++){
			Entity e1 = entities.get(i);
			float e1x = e1.getX();
			float e1y = e1.getY();
			float dx = e1x - ex;
			float dy = e1y - ey;
			float dist = (float) Math.sqrt((dx*dx) + (dy*dy));
			if(dist <= r) target.add(e);
		}
		return target;
	}
	
	public List<Player> getTPlayer(Entity e, int r){
		List<Player> target = new ArrayList<Player>();
		float ex = e.getX();
		float ey = e.getY();
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i) instanceof Player){
				Player p1 = (Player) entities.get(i);
				float p1x = p1.getX();
				float p1y = p1.getY();
				float dx = p1x - ex;
				float dy = p1y - ey;
				float dist = (float) Math.sqrt((dx*dx) + (dy*dy));
				if(dist <= r) target.add(p1);
			}
		}
		return target;
	}
	
	public void add(Entity e){
		entities.add(e);
	}
	
	public void remove(Entity e){
		entities.remove(e);
	}
	
	public void addParticle(Particle part){
		particles.add(part);
	}
	
	public void removeParticle(Particle part){
		particles.remove(part);
	}
	
	public void addProjectile(Projectile pr){
		projectiles.add(pr);
	}
	
	public void removeProjectile(Projectile pr){
		projectiles.remove(pr);
	}

	public Handy getHand() {
		return hand;
	}

	public void setHand(Handy hand) {
		this.hand = hand;
	}

	public Player getP() {
		return p;
	}
	

	public void setP(Player p1) {
		p = p1;
	}
	
	public Unknown getU(){
		return u;
	}
	
	public void setU(Unknown u1){
		u = u1;
	}
	
	public ArrayList<Entity> getEntities() {
		
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

	public void setParticles(ArrayList<Particle> particles) {
		this.particles = particles;
	}
	
	
	
}
