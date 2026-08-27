module.exports = {
  apps: [
    {
      name: 'media-service',
      script: '/usr/bin/java',
      args: ['-jar', '-Dserver.port=8083', '/opt/app/media-service.jar'],
      instances: 1,
      autorestart: true,
      watch: false,
      max_memory_restart: '1G',
      log_date_format: 'YYYY-MM-DD HH:mm:ss Z',
      error_file: '/var/log/app/media-service-error.log',
      out_file: '/var/log/app/media-service-out.log',
      merge_logs: true,
      env: {
        SPRING_PROFILES_ACTIVE: 'prod'
      }
    }
  ]
};
