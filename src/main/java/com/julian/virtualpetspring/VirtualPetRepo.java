package com.julian.virtualpetspring;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

@Repository
public class VirtualPetRepo
{
	public Map<Long, VirtualPet> pets = new HashMap<>();
	public Map<VirtualPet, CageForDog> cagesForDogs = new HashMap<>();
	
	private final int MAX_LITTERBOX_WASTE = 10;
	private int shelterLitterBoxWaste = 0;
	private boolean isLitterBoxClean = false;
	
	/*
	 * 
	 * METHODS
	 * 
	 */
	
	public VirtualPetRepo()
	{
		OrganicPet pet1 = new OrganicDog(1L, "Jojo", "Jojo's Bizarre Adventure", 75);
		OrganicPet pet5 = new OrganicDog(2L, "Jack", "blank", 75);
		OrganicPet pet6 = new OrganicDog(3L, "Jim", "blank", 75);

		RoboticPet pet2 = new RoboticDog(4L, "Vash", "Trigun", 100, 90);
		OrganicPet pet3 = new OrganicCat(5L, "Nail", "A band?", 66);
		RoboticPet pet4 = new RoboticCat(6L, "Blue", "Lugato from Trigun", 100, 50);

		this.addPet(pet5);
		this.addPet(pet6);
		this.addPet(pet1);
		this.addPet(pet2);
		this.addPet(pet3);
		this.addPet(pet4);
	}

	public void addPet(VirtualPet pet)
	{
		if(pet instanceof OrganicDog)
		{
			addDogToNewCage((OrganicDog) pet);
		}
		pets.put(pet.getId(), pet);
	}

	public void adoptPet(VirtualPet pet)
	{
		pets.remove(pet.getName(), pet);
	}
	
	public VirtualPet returnSpecificPet(Long id)
	{
		return pets.get(id);
	}
	
	// ROBOTIC PET SPECIFIC
	
	public void oilAllRoboticPets()
	{
		for (RoboticPet robotPet : getRoboticPets())
		{
			robotPet.oilPet();
		}
	}
	
	public void disassembleRobotPet(RoboticPet pet)
	{
		pets.remove(pet.getId());
	}
	
	// ORGANIC PET SPECIFIC
	
	public void playWithAll()
	{
		for (VirtualPet pet : getAllPets())
		{
			if (pet instanceof OrganicPet)
			{
				((OrganicPet)pet).play();	
			}
		}
	}
	
	public void feedAllOrganicPets()
	{
		for (OrganicPet pet : getOrganicPets())
		{
			pet.feed();
		}
	}
	
	// CAT SPECIFIC
	
	public int getLitterBoxWasteValue() {
		return shelterLitterBoxWaste;
	}

	public boolean isLitterBoxClean() {
		return isLitterBoxClean;
	}
	
	public boolean litterBoxIsDirtyToggle()
	{
		return isLitterBoxClean = !isLitterBoxClean;
	}
	
	public void cleanLitterBox()
	{
		shelterLitterBoxWaste = 0;
		isLitterBoxClean = litterBoxIsDirtyToggle();
	}
	
	public void catUsesLitterBox()
	{
		shelterLitterBoxWaste += 1;
		if(shelterLitterBoxWaste == MAX_LITTERBOX_WASTE && !isLitterBoxClean)
		{
			isLitterBoxClean = litterBoxIsDirtyToggle();
		}
	}
	
	// DOG SPECIFIC
	
	public void walkOneDog(OrganicDog dog)
	{
		dog.walk();
	}
	
	public void walkAllOrganicDogs()
	{
		for (OrganicPet dog : getOrganicPets())
		{
			if(dog instanceof OrganicDog)
			{
				((OrganicDog) dog).walk();
			}
		}
	}
	
	public void cleanAllDogCages()
	{
		for (CageForDog cage : cagesForDogs.values()) {
			cage.cleanCage();
		}
	}
	
	public void addDogToNewCage(OrganicDog dog)
	{
		cagesForDogs.put(dog, new CageForDog(dog.getName()));
	}
	
	public int getNumberOfCages()
	{
		return cagesForDogs.size();
	} 
	
	public void cleanCage(OrganicDog dog)
	{
		cagesForDogs.get(dog).cleanCage();
	}
	
	/*
	 * 
	 * COLLECTIONS
	 * 
	 */
	
	public Collection<VirtualPet> getAllPets()
	{
		return pets.values();
	}
	
	public Collection<OrganicPet> getOrganicPets()
	{
		Set<OrganicPet> organicPets = new HashSet<>();
		
		for (VirtualPet pet : pets.values())
		{
			if (pet instanceof OrganicPet)
			{
				organicPets.add((OrganicPet)pet);
			}
		}
		return organicPets;
	}

	public Collection<RoboticPet> getRoboticPets()
	{
		Set<RoboticPet> roboPets = new HashSet<>();
		for (VirtualPet pet : pets.values())
		{
			if (pet instanceof RoboticPet)
			{
				roboPets.add((RoboticPet)pet);
			}
		}
		return roboPets;
	}
	
	// TICK
	
	public void shelterTick()
	{
		for (CageForDog cage : cagesForDogs.values()) {
			cage.tick();
		}
		
		for (VirtualPet pet : getAllPets())
		{
			pet.tick();
		}
	}
}
