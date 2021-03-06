package topologyV2;

import java.util.ArrayList;

public class Components {
	public ArrayList<Component> components;

	public int size() {
		return components.size();
	}
	
	public Components(Boundary boundary) {
		components = new ArrayList<Component>();
		Tag tag = new Tag(boundary);
		Point seedPoint = tag.SeedPoint();
		while (seedPoint != null) {
			Component newComponent = new Component(tag, seedPoint);
			components.add(newComponent);
			seedPoint = tag.SeedPoint();
		}
	}
}
