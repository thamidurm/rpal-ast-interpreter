package com.thamidurm.cse_machine.environment;

import java.util.HashMap;

/**
 * Class representing an environment in the CSE machine
 */
public class Environment {
    private Environment parent;
    private final int id;
    private static int count = 0;
    private final HashMap<String, BoundValue> hashMap;
    private  static final HashMap<Integer,Environment> envs = new HashMap<>();

    public Environment(){
        this.parent = null;
        this.id = count++;
        this.hashMap = new HashMap<>();
        envs.put(this.id, this);
    }

    public  Environment(Environment parent){
        this();
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    /** Returns the environment with the given id if found. Otherwise, exits
     * the program
     * @param id id of an environment
     * @return the environment with the given id
     */
    public static Environment getEnv(int id){
        if (envs.containsKey(id)){
            return envs.get(id);
        }else{
            System.out.println("Invalid environment requested");
            System.exit(-1);
            return null;
        }
    }

    /**
     * Returns the bound value of a name if found in the current environment or parent environments.
     * If no bound value is found, exits the program.
     * @param environment the environment to be searched
     * @param key the name to be searched
     * @return the bound value related the key
     */
    private static BoundValue searchUtil(Environment environment, String  key) {
        if(environment == null){
            System.out.println("Undeclared identifier: <ID:" + key + ">");
            System.exit(-1);
            return null;
        }else if (environment.getHashMap().containsKey(key)){
            return environment.getHashMap().get(key);
        }else{
            return searchUtil(environment.parent, key);
        }
    }

    /** Calls searchUtil static function using the current object and given key
     * @param key the name of the needed value
     * @return bound value related to the given key
     */
    public BoundValue getValue(String key){
        return searchUtil(this, key);
    }

    /** Bind the value to the key by adding it the this environment's hashmap
     * using the name as the key
     * @param key the name
     * @param value the value
     */
    public void addValue(String key, BoundValue value){
        this.hashMap.put(key, value);
    }

//    public void setParent(Environment parent) {
//        this.parent = parent;
//    }

    public HashMap<String, BoundValue> getHashMap() {
        return hashMap;
    }
}
