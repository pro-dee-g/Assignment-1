
import java.util.*;

class Main {
    public static void main(String[] args) {
        Dog dog1 = new Dog("Tommy", "Dog", 2, HealthStatus.HEALTHY, false, "Labrador", true);
        Bird bird1 = new Bird("Polly", "Parrot", 1, HealthStatus.HEALTHY, false, 2.5, true);
        Cat cat1 = new Cat("Kitty", "Cat", 3, HealthStatus.UNHEALTHY, false, "White", true);
        Staff staff1 = new Staff(1, "John", Role.EMPLOYEE, new ArrayList<>());
        Adopter adopter1 = new Adopter(1, "Alice", "1234567890", new HashSet<>());
        AnimalManager.addAnimals(dog1);
        AnimalManager.addAnimals(bird1);
        AnimalManager.addAnimals(cat1);
        AnimalManager.viewAnimals();
        AnimalManager.adoptAnimal(adopter1, dog1);
        AnimalManager.adoptAnimal(adopter1, dog1);
        AnimalManager.updateHealth(cat1, HealthStatus.HEALTHY);
        staff1.assign_task("Feed the animals");
        staff1.assign_task("Clean the cages");
        staff1.display_tasks();
        AnimalManager.adoptAnimal(adopter1, cat1);
        AnimalManager.viewAnimals();
    }
}

abstract class Animal {
    protected String name, species;
    protected int age;
    protected HealthStatus health_status;
    protected boolean adoption_status;

    public Animal(String name, String species, int age, HealthStatus health_status, boolean adoption_status) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.health_status = health_status;
        this.adoption_status = adoption_status;
    }


    public void display_info() {
        String availableForAdoption = adoption_status ? "is available for adoption" :  "is not available for adoption";
        System.out.println(this.name + " is a " + this.species + " and is in " + health_status + " state and " + availableForAdoption);
    }
    
    public void update_health_status(HealthStatus status) {
        this.health_status = status;
    }


    @Override
    public String toString() {
        return "Animal [name=" + name + ", species=" + species + ", age=" + age + ", health_status=" + health_status
                + ", adoption_status=" + adoption_status + "]";
    }
    
}

class Dog extends Animal {
    private String breed; 
    private boolean trained; 

    public Dog(String name, String species, int age, HealthStatus health_status, boolean adoption_status, String breed,
            boolean trained) {
        super(name, species, age, health_status, adoption_status);
        this.breed = breed;
        this.trained = trained;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public boolean isTrained() {
        return trained;
    }

    public void setTrained(boolean trained) {
        this.trained = trained;
    }
}

class Cat extends Animal {
    private String color;
    private boolean indoor;

    public Cat(String name, String species, int age, HealthStatus health_status, boolean adoption_status, String color,
            boolean indoor) {
        super(name, species, age, health_status, adoption_status);
        this.color = color;
        this.indoor = indoor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }
}

class Bird extends Animal {
    private double wing_span; 
    private boolean can_fly; 

    public Bird(String name, String species, int age, HealthStatus health_status, boolean adoption_status,
            double wing_span, boolean can_fly) {
        super(name, species, age, health_status, adoption_status);
        this.wing_span = wing_span;
        this.can_fly = can_fly;
    }

    
    public double getWing_span() {
        return wing_span;
    }

    public void setWing_span(double wing_span) {
        this.wing_span = wing_span;
    }

    public boolean isCan_fly() {
        return can_fly;
    }

    public void setCan_fly(boolean can_fly) {
        this.can_fly = can_fly;
    }

    @Override
    public void display_info() {
        String availableForAdoption = adoption_status ? "is available for adoption" :  "is not available for adoption";
        System.out.println("The bird " + this.name + " is a " + this.species + " and is in " + health_status + " state and " + availableForAdoption);
    }
}

class Staff {
    private int staff_id;
    private String name;
    private Role role;
    List<String> tasks;

    public Staff(int staff_id, String name, Role role, List<String> tasks) {
        this.staff_id = staff_id;
        this.name = name;
        this.role = role;
        this.tasks = tasks;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<String> getTasks() {
        return List.copyOf(tasks);
    }

    public void assign_task(String task) {
        tasks.add(task);
    }
    
    public void display_tasks() {
        System.out.println("Tasks for employee " + this.name + " with employee id: " + this.staff_id + " => " + tasks);
    }
}

class Adopter {
    private int adopter_id;
    private String name;
    private String contact_info;
    private Set<Animal> adopted_animals;

    public Adopter(int adopter_id, String name, String contact_info, Set<Animal> adopted_animals) {
        this.adopter_id = adopter_id;
        this.name = name;
        this.contact_info = contact_info;
        this.adopted_animals = adopted_animals;
    }

    public int getAdopter_id() {
        return adopter_id;
    }

    public void setAdopter_id(int adopter_id) {
        this.adopter_id = adopter_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_info() {
        return contact_info;
    }

    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }

    public void adopt_animal(Animal animal) {
        adopted_animals.add(animal);
    }
}

class AnimalManager {
    private static Set<Animal> animals = new HashSet<>();

    public static void addAnimals(Animal animal) {
        animals.add(animal);
    }
    
    public static void viewAnimals() {
        List<Animal> dogs = animals.stream().filter(animal -> (animal instanceof Dog)).toList();
        if(!dogs.isEmpty())System.out.println("Dogs: " + dogs);
        List<Animal> cats = animals.stream().filter(animal -> (animal instanceof Cat)).toList();
        if(!cats.isEmpty())System.out.println("Cats: " + cats);
        List<Animal> birds = animals.stream().filter(animal -> (animal instanceof Bird)).toList();
        if(!birds.isEmpty())System.out.println("Bords: " + birds);
    }
    
    public static void adoptAnimal(Adopter adopter, Animal animal) {
        if(animal.adoption_status) {
            System.out.println("Animal is already adopted." + animal);
            return;
        }
        animal.adoption_status = true;
        animals.remove(animal);
        adopter.adopt_animal(animal);
    }
    
    public static void updateHealth(Animal animal, HealthStatus status) {
        animal.update_health_status(status);
        animals.add(animal);
    }
}

enum HealthStatus {
    HEALTHY, UNHEALTHY;
}

enum Role {
    ADMIN, EMPLOYEE;
}