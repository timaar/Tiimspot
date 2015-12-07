'use strict';

angular.module('tiimspotApp').controller('PersoonDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Persoon', 'Adres', 'Contactinfo', 'Comment', 'Ouder',
        function($scope, $stateParams, $modalInstance, $q, entity, Persoon, Adres, Contactinfo, Comment, Ouder) {

        $scope.persoon = entity;
        $scope.adress = Adres.query({filter: 'persoon-is-null'});
        $q.all([$scope.persoon.$promise, $scope.adress.$promise]).then(function() {
            if (!$scope.persoon.adres || !$scope.persoon.adres.id) {
                return $q.reject();
            }
            return Adres.get({id : $scope.persoon.adres.id}).$promise;
        }).then(function(adres) {
            $scope.adress.push(adres);
        });
        $scope.contactinfos = Contactinfo.query({filter: 'persoon-is-null'});
        $q.all([$scope.persoon.$promise, $scope.contactinfos.$promise]).then(function() {
            if (!$scope.persoon.contactinfo || !$scope.persoon.contactinfo.id) {
                return $q.reject();
            }
            return Contactinfo.get({id : $scope.persoon.contactinfo.id}).$promise;
        }).then(function(contactinfo) {
            $scope.contactinfos.push(contactinfo);
        });
        $scope.comments = Comment.query();
        $scope.ouders = Ouder.query();
        $scope.load = function(id) {
            Persoon.get({id : id}, function(result) {
                $scope.persoon = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('tiimspotApp:persoonUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.persoon.id != null) {
                Persoon.update($scope.persoon, onSaveSuccess, onSaveError);
            } else {
                Persoon.save($scope.persoon, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
