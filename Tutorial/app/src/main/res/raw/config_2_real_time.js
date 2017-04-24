{
    "real_time": true,
    "bones": [
        {
            "name": "RightUpperArm",
            "color1": "Blue",
            "color2": "Blue",
            "frequency": 20
        },
        {
            "name": "RightForeArm",
            "color1": "Red",
            "color2": "Red",
            "frequency": 20
        }
    ],
    "master_bone": "RightUpperArm",
    "special": {
        "bone": "RightUpperArm",
        "orientation": "Front"
    },
    "constraints": [
        {
            "type": "INTERP",
            "target": "Tummy",
            "source": "ChestBottom",
            "f": 0.5
        },
        {
            "type": "INTERP",
            "target": "ChestTop",
            "source": "Hip",
            "f": -0.42
        }
    ]
}
